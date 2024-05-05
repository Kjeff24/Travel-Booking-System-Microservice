import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-create-accommodation',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './create-update-accommodation.component.html',
  styleUrl: './create-update-accommodation.component.css',
})
export class CreateUpdateAccommodationComponent {
  isUpdate: boolean = false;

  constructor(private route: ActivatedRoute, private location: Location) {}

  ngOnInit(): void {
    const currentUrl = this.location.path();
    if (currentUrl.includes('update')) {
      this.isUpdate = true;
    }
  }
}
