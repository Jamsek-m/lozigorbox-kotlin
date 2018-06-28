import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {CoreRoutingModule} from "./core-routing.module";
import {RouterModule} from "@angular/router";
import {HeaderComponent} from "./header/header.component";
import {Napaka404Component} from "./napake/404/napaka404.component";
import {Napaka403Component} from "./napake/403/napaka403.component";
import {BsDropdownModule} from "ngx-bootstrap";
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {TranslateHttpLoader} from "@ngx-translate/http-loader";
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from "@angular/common/http";
import {LanguageService} from "./services/language/language.service";
import {AuthenticationService} from "./services/authentication/authentication.service";
import {HeaderInterceptor} from "./services/authentication/header.interceptor";
import {LoginComponent} from "./login/login.component";
import {AuthenticatedGuard} from "./services/authentication/authenticated.guard";
import {SharedModule} from "../shared/shared.module";
import {FormsModule} from "@angular/forms";
import {HasRoleGuard} from "./services/authentication/has-role.guard";

export function HttpLoaderFactory(http: HttpClient) {
    return new TranslateHttpLoader(http);
}

@NgModule({
    imports: [
        CommonModule,
        CoreRoutingModule,
        HttpClientModule,
        SharedModule,
        FormsModule,
        // bootstrap dropdown
        BsDropdownModule.forRoot(),
        // ngx-translate
        TranslateModule.forRoot({
            loader: {
                provide: TranslateLoader,
                useFactory: HttpLoaderFactory,
                deps: [HttpClient]
            }
        })
    ],
    declarations: [
        HeaderComponent,
        LoginComponent,
        Napaka404Component,
        Napaka403Component
    ],
    providers: [
        LanguageService,
        AuthenticationService,
        {provide: HTTP_INTERCEPTORS, useClass: HeaderInterceptor, multi: true},
        AuthenticatedGuard,
        HasRoleGuard
    ],
    exports: [
        RouterModule,
        HeaderComponent
    ]
})
export class CoreModule {
}
