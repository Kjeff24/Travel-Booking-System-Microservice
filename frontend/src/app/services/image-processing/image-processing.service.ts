import { Injectable } from '@angular/core';
import { CarRentalItem } from '../../models/car-rental-item';
import { FileHandle } from '../../models/file-handle';
import { DomSanitizer } from '@angular/platform-browser';
import { CategoryItem } from '../../models/category-item';

@Injectable({
  providedIn: 'root'
})
export class ImageProcessingService {

  constructor(private sanitizer: DomSanitizer) { }
  
  public createCarRentalImage(product: CarRentalItem) {
    const productImage: any = product.carImage;
    const imageBlob = this.dataUIRtoBlob(productImage.picByte, productImage.type)
    const imageFile = new File([imageBlob], productImage.name, {type: productImage.type});
    const finalFileHandle: FileHandle = {
      file: imageFile,
      url: this.sanitizer.bypassSecurityTrustUrl(window.URL.createObjectURL(imageFile))
    };

    product.carImage = finalFileHandle;
    return product;

  }

  public createCategoryImage(product: CategoryItem) {
    const productImage: any = product.icon;
    const imageBlob = this.dataUIRtoBlob(productImage.picByte, productImage.type)
    const imageFile = new File([imageBlob], productImage.name, {type: productImage.type});
    const finalFileHandle: FileHandle = {
      file: imageFile,
      url: this.sanitizer.bypassSecurityTrustUrl(window.URL.createObjectURL(imageFile))
    };

    product.icon = finalFileHandle;
    return product;

  }

  public dataUIRtoBlob(picBytes, imageType) {
    const byteString = window.atob(picBytes);
    const arrayBuffer = new ArrayBuffer(byteString.length);
    const int8Array = new Uint8Array(arrayBuffer);

    for(let i = 0; i < byteString.length; i++) {
      int8Array[i] = byteString.charCodeAt(i);
    }

    const blob = new Blob([int8Array], {type: imageType});
    return blob
  }
}
