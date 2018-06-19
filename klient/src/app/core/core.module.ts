import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {CoreRoutingModule} from "./core-routing.module";
import {RouterModule} from "@angular/router";
import {HeaderComponent} from "./header/header.component";
import {Napaka404Component} from "./napake/404/napaka404.component";
import {Napaka403Component} from "./napake/403/napaka403.component";

@NgModule({
    imports: [
        CommonModule,
        CoreRoutingModule
    ],
    declarations: [
        HeaderComponent,
        Napaka404Component,
        Napaka403Component
    ],
    providers: [],
    exports: [
        RouterModule,
        HeaderComponent
    ]
})
export class CoreModule {
}
