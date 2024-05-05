import { CommonModule, Location } from '@angular/common';
import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-create-flight',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './create-update-flight.component.html',
  styleUrl: './create-update-flight.component.css'
})
export class CreateUpdateFlightComponent {

  isUpdate: boolean = false;

  constructor(private route: ActivatedRoute, private location: Location) {}

  ngOnInit(): void {
    const currentUrl = this.location.path();
    if (currentUrl.includes('update')) {
      this.isUpdate = true;
    }
  }
}
