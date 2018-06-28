export class PrijavaRequest {

    public email: string;
    public geslo: string;

    constructor() {

    }

    public static from(email: string, geslo: string): PrijavaRequest {
        const req = new PrijavaRequest();
        req.email = email;
        req.geslo = geslo;
        return req;
    }

    public isEmpty(): boolean {
        return !this.geslo && !this.email;
    }

    public gesloNotSet(): boolean {
        return !this.geslo;
    }

    public emailNotSet(): boolean {
        return !this.email;
    }

}
