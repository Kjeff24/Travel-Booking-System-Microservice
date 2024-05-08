import { FileHandle } from "./file-handle";

export class CarRentalItem{
    id: string = '';
    carType: string = '';
    price: number = 0;
    categoryId: string = '';
    carImage: FileHandle;
  
    constructor() {}
}
