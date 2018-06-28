export class AlertSporocila {
    private alerti: NotificationAlert[];

    constructor() {
        this.alerti = [];
    }

    public pridobiAlerte(): NotificationAlert[] {
        return this.alerti;
    }

    public dodajAlert(tip: NotificationAlertType, sporocilo: string): void {
        this.alerti.push(new NotificationAlert(tip, sporocilo));
    }

    public pocistiAlerte(): void {
        this.alerti = [];
    }

}

export class NotificationAlert {

    public tip: NotificationAlertType;
    public sporocilo: string;

    constructor(tip: NotificationAlertType, spor: string) {
        this.sporocilo = spor;
        this.tip = tip;
    }

}

export enum NotificationAlertType {
    SUCCESS = "success",
    DANGER = "danger",
    INFO = "info",
    WARNING = "warning"
}
