import {Component, OnInit} from "@angular/core";
import {PrijavaRequest} from "../../entities/requests/prijava.request";
import {AlertSporocila, NotificationAlertType} from "../../shared/alert/notification.model";
import {TranslateService} from "@ngx-translate/core";
import {AuthenticationService} from "../services/authentication/authentication.service";
import {LanguageService} from "../services/language/language.service";
import {PrijavaResponse} from "../../entities/responses/prijava.response";
import {HttpErrorResponse} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
    selector: "app-login-page",
    templateUrl: "login.component.html",
    styleUrls: ["login.component.css"]
})
export class LoginComponent implements OnInit {
    podatkiZaPrijavo: PrijavaRequest;
    alerti: AlertSporocila;
    returnUrl: string;

    constructor(private language: LanguageService,
                private auth: AuthenticationService,
                private router: Router,
                private activatedRoute: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.activatedRoute.queryParams.subscribe(params => {
            this.returnUrl = params["return"] || "/";
        });

        this.podatkiZaPrijavo = new PrijavaRequest();
        this.alerti = new AlertSporocila();
    }

    prijava() {
        if (this.podatkiZaPrijavo.isEmpty()) {
            this.language.pridobiPrevod("NAVIGATION.LOGIN.ERROR.EMPTY_DATA").subscribe(res => {
                this.alerti.dodajAlert(NotificationAlertType.DANGER, res);
            });
        } else {
            this.auth.prijaviUporabnika(this.podatkiZaPrijavo).subscribe(
                (res: PrijavaResponse) => {
                    this.router.navigateByUrl(this.returnUrl);
                },
                (err: HttpErrorResponse) => {
                    this.language.pridobiPrevod(err.error.napaka).subscribe(res => {
                        this.alerti.pocistiAlerte();
                        this.alerti.dodajAlert(NotificationAlertType.DANGER, res);
                    });
                }
            );
        }
    }

}
