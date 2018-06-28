
export enum LOCALE {
    EN = "en",
    SL = "sl"
}

export namespace LOCALE {

    export function getAll(): string[] {
        return Object.keys(LOCALE)
            .map(key => LOCALE[key])
            .filter(item => {
                return typeof item === "string";
            });
    }

}
