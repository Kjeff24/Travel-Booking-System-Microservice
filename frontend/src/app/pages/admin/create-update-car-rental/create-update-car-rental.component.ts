import { CommonModule, Location } from '@angular/common';
import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-create-car-rental',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './create-update-car-rental.component.html',
  styleUrl: './create-update-car-rental.component.css'
})
export class CreateUpdateCarRentalComponent {

  isUpdate: boolean = false;

  constructor(private route: ActivatedRoute, private location: Location) {}

  ngOnInit(): void {
    const currentUrl = this.location.path();
    if (currentUrl.includes('update')) {
      this.isUpdate = true;
    }
  }
}
