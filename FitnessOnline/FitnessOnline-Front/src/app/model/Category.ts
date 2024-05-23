import { SpecificAttribute } from './SpecificAttribute';

export class Category {
    id: number = -1;
    name: string = '-';
    specificAttributes: Array<SpecificAttribute> = [];
}