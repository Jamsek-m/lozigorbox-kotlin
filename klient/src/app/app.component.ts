import {Component} from "@angular/core";
import {LanguageService} from "./core/services/language/language.service";
import {AuthenticationService} from "./core/services/authentication/authentication.service";

@Component({
    selector: "app-root",
    templateUrl: "./app.component.html",
    styleUrls: ["./app.component.css"]
})
export class AppComponent {

    constructor(private language: LanguageService, private auth: AuthenticationService) {
        this.language.inicializirajPrevode();
        /*this.auth.prijavaJeAktivna().subscribe((aktivna: boolean) => {
            this.auth.loginEvent.emit(aktivna);
        });*/
    }
}
