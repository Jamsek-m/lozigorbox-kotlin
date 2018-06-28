import {Injectable} from "@angular/core";
import {TranslateService} from "@ngx-translate/core";
import {LOCALE} from "./locale.enum";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";

@Injectable({
    providedIn: "root"
})
export class LanguageService {

    private LOCAL_STORAGE_LANG = "lozigorbox_lang";
    constructor(private translate: TranslateService) {
    }

    public inicializirajPrevode(): void {
        this.translate.addLangs(LOCALE.getAll());
        const trenutniLocale: string = this.pridobiTrenutniJezik();
        this.nastaviJezik(trenutniLocale);
        this.translate.setDefaultLang(trenutniLocale);
    }

    // prebere localstorage in nastavi jezik - ce ni nastavljen potem izbere browser lang
    public pridobiTrenutniJezik(): string {
        const localStored = localStorage.getItem(this.LOCAL_STORAGE_LANG);
        if (!localStored) {
            return this.getDefaultLanguage();
        }
        return localStored;
    }

    public pridobiPrevod(prevodId: string): Observable<string> {
        return this.translate.get(prevodId).pipe(map(res => res as string));
    }

    public nastaviJezik(locale: string): void {
        this.translate.use(locale);
        localStorage.setItem(this.LOCAL_STORAGE_LANG, locale);
    }

    private getDefaultLanguage() {
        const browserLang = this.translate.getBrowserLang();
        if (browserLang === LOCALE.SL) {
            return browserLang;
        } else {
            return LOCALE.EN;
        }
    }
}
