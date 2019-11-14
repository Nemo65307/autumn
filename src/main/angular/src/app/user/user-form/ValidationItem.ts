export class ValidationItem {
    field: string;
    messages: string[];

    constructor(field: string, messages: string[]) {
        this.field = field;
        this.messages = messages;
    }
}
