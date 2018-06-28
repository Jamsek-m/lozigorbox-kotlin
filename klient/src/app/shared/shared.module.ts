import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {AlertModule} from "ngx-bootstrap";
import {AlertsComponent} from "./alert/alerts.component";

@NgModule({
    imports: [
        CommonModule,
        AlertModule.forRoot()
    ],
    declarations: [
        AlertsComponent
    ],
    exports: [
        AlertsComponent
    ]
})
export class SharedModule {
}
