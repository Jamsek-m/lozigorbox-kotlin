import {Component, OnInit} from "@angular/core";
import {LanguageService} from "../services/language/language.service";
import {LOCALE} from "../services/language/locale.enum";
import {AuthenticationService} from "../services/authentication/authentication.service";
import {Router} from "@angular/router";
import {p} from "@angular/core/src/render3";
import {Uporabnik} from "../../entities/models/uporabnik.model";

@Component({
    selector: "app-header",
    templateUrl: "header.component.html",
    styleUrls: ["header.component.css"]
})
export class HeaderComponent implements OnInit {
    trenutniLocale: string;
    availableLocales: string[];
    trenutniUporabnik: Uporabnik;
    prijavljen: boolean;

    constructor(private language: LanguageService, private auth: AuthenticationService, private router: Router) {
    }

    ngOnInit(): void {
        this.trenutniUporabnik = Uporabnik.empty();
        this.trenutniLocale = this.language.pridobiTrenutniJezik();
        this.availableLocales = LOCALE.getAll();
        this.auth.prijavaJeAktivna().subscribe((aktivna: boolean) => {
            this.prijavljen = aktivna;
            this.posodobiUporabnika(this.prijavljen);
        });
        this.auth.loginEvent.subscribe((prijavljen: boolean) => {
            this.prijavljen = prijavljen;
            this.posodobiUporabnika(this.prijavljen);
        });
    }

    spremeniJezik(locale) {
        this.trenutniLocale = locale;
        this.language.nastaviJezik(locale);
    }

    odjava() {
        this.auth.odjaviUporabnika().subscribe(
            () => {
                this.router.navigateByUrl("/login");
            },
            (err) => {

            }
        );
    }

    private posodobiUporabnika(prijavljen: boolean) {
        if (prijavljen) {
            this.auth.getTrenutniUporabnik().subscribe((uporabnik: Uporabnik) => {
                this.trenutniUporabnik = uporabnik;
            });
        }
    }

}
