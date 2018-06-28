import {Sifrant} from "./sifrant-abstract.model";

export class Uporabnik {
    public id: number;
    public email: string;
    public ime: string;
    public status: UporabnikStatus;
    public vloge: Vloga[];

    constructor() {

    }

    public static empty(): Uporabnik {
        const up = new Uporabnik();
        up.vloge = [];
        up.status = new UporabnikStatus();
        return up;
    }
}

export class UporabnikStatus extends Sifrant {
    constructor() {
        super();
    }
}

export class Vloga extends Sifrant {
    constructor() {
        super();
    }
}

export enum VlogaTypes {
    USER = "USER",
    ADMIN = "ADMIN",
    MOD = "MOD"
}
