import {EventEmitter, Injectable, Output} from "@angular/core";
import {HttpClient, HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {PrijavaRequest} from "../../../entities/requests/prijava.request";
import {Observable, of} from "rxjs";
import {PrijavaResponse} from "../../../entities/responses/prijava.response";
import {environment} from "../../../../environments/environment";
import {catchError, map, tap} from "rxjs/operators";
import {Uporabnik, VlogaTypes} from "../../../entities/models/uporabnik.model";


@Injectable({
    providedIn: "root"
})
export class AuthenticationService {
    private URL = `${environment.api}/avtentikacija`;

    @Output()
    public loginEvent: EventEmitter<boolean> = new EventEmitter<boolean>();

    constructor(private http: HttpClient) {
    }

    public prijaviUporabnika(zahteva: PrijavaRequest): Observable<PrijavaResponse> {
        const url = `${this.URL}/prijava`;
        return this.http.post(url, JSON.stringify(zahteva)).pipe(
            tap((res) => {
                this.notifyLoginEvent(true);
            }),
            map(res => res as PrijavaResponse),
            catchError((err: HttpErrorResponse) => {
                this.notifyLoginEvent(false);
                throw err;
            })
        );
    }

    public prijavaJeAktivna(): Observable<boolean> {
        const url = `${this.URL}/je-prijavljen`;
        return this.http.get(url).pipe(
            map(res => res as boolean),
            catchError((err: HttpErrorResponse) => of(false))
        );
    }

    public imaVlogo(vloga: VlogaTypes): Observable<boolean> {
        const url = `${this.URL}/ima-vlogo?vloga=${vloga}`;
        return this.http.get(url).pipe(
            map(res => res as boolean),
            catchError((err: HttpErrorResponse) => of(false))
        );
    }

    public imaEnoOdVlog(vloge: VlogaTypes[]): Observable<boolean> {
        let url = `${this.URL}/ima-vloge?`;
        vloge.forEach(vl => {
            url += `vloge=${vl}&`;
        });
        url = url.slice(0, -1);

        return this.http.get(url).pipe(
            map(res => res as boolean),
            catchError((err: HttpErrorResponse) => of(false))
        );
    }

    public odjaviUporabnika(): Observable<void> {
        const url = `${this.URL}/odjava`;
        return this.http.get(url).pipe(
            tap(() => {
                this.notifyLoginEvent(false);
            }),
            map(() => {
                return Observable.create();
            })
        );
    }

    public getTrenutniUporabnik(): Observable<Uporabnik> {
        const url = `${this.URL}/trenutni-uporabnik`;
        return this.http.get(url).pipe(map(res => res as Uporabnik));
    }

    private notifyLoginEvent(state: boolean): void {
        this.loginEvent.emit(state);
    }

}
