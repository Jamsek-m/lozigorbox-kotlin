import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../../environments/environment.prod";
import {HeaderTypes, MediaType} from "./http.constants";

@Injectable()
export class HeaderInterceptor implements HttpInterceptor {

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
            if (this.jeBlacklistan(req.url)) {
                return next.handle(req);
            }
            const headers: HttpHeaders = req.headers
                .set(HeaderTypes.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true")
                .set(HeaderTypes.CONTENT_TYPE, MediaType.APP_JSON)
                .set(HeaderTypes.ACCEPT, MediaType.APP_JSON);
            const clonedRequest: HttpRequest<any> = req.clone({
                headers,
                withCredentials: true
            });
            return next.handle(clonedRequest);
    }

    private jeBlacklistan(url: string): boolean {
        const base = environment.api;
        const uri = url.replace(base, "");
        if (this.jeAsset(uri)) {
            return true;
        }
        // TODO: dodaj svoje poti
        if (uri.match(/\/uvoz\/studenti/)) {
            return true;
        }
        if (uri.match(/\/predmet\/stevilo\/izvoz\/.{3}\/\d{4}\/.*\/\d/)) {
            return true;
        }
        /* primer blacklista nekega linka:
           imas url http://localhost:8080/v1/storitev/5
           hendlas samo uri brez v1, torej /storitev/5
           if(uri.match(/\/storitev\/\d+/)) { return true; }
        */
        return false;
    }

    private jeAsset(uri: string): boolean {
        const urinoprefix = uri.replace("/", "");
        return urinoprefix.startsWith("assets");
    }

}
