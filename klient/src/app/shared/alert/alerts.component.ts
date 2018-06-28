///<reference path="../../../../node_modules/@types/node/index.d.ts"/>
import {Component, Input, OnInit} from "@angular/core";
import {AlertSporocila} from "./notification.model";

@Component({
    selector: "app-shared-alerts",
    moduleId: module.id,
    template: `
        <div>
            <alert *ngFor="let al of alerti.pridobiAlerte()" [dismissible]="true"
                   [dismissOnTimeout]="5000" [type]="al.tip">
                {{al.sporocilo}}
            </alert>
        </div>
    `
})
export class AlertsComponent implements OnInit {
    @Input() alerti: AlertSporocila;

    constructor() {
    }

    ngOnInit(): void {
    }

}
