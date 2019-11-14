import {ValidationItem} from './ValidationItem';

export class ValidationResult {

    message: string;
    items: ValidationItem[];

    constructor(message: string, items: ValidationItem[]) {
        this.message = message;
        this.items = items;
    }

}
