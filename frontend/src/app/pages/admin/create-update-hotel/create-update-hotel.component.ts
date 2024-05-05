import { CommonModule, Location } from '@angular/common';
import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-create-hotel',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './create-update-hotel.component.html',
  styleUrl: './create-update-hotel.component.css'
})
export class CreateUpdateHotelComponent {

  isUpdate: boolean = false;

  constructor(private location: Location) {}

  ngOnInit(): void {
    const currentUrl = this.location.path();
    if (currentUrl.includes('update')) {
      this.isUpdate = true;
    }
  }
}
