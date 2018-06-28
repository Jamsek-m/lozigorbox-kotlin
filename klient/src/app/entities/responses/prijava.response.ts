import {Uporabnik} from "../models/uporabnik.model";

export interface PrijavaResponse {
    expiration: Date;
    uporabnik: Uporabnik;
}
