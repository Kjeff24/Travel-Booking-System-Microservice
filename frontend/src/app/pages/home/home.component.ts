import { Component, OnInit, ViewChild } from '@angular/core';
import { NavbarComponent } from '../../components/navbar/navbar.component';
import { FooterComponent } from '../../components/footer/footer.component';
import {
  NavigationEnd,
  Router,
  RouterLink,
} from '@angular/router';
import { CategoryCardComponent } from '../../components/category-card/category-card.component';
import { CategoryItem } from '../../models/category-item';
import { CommonModule } from '@angular/common';
import { CategoryService } from '../../services/category/category.service';
import { MiniAboutComponent } from '../../components/mini-about/mini-about.component';
import { TokenService } from '../../services/token/token.service';
import { UserstateComponent } from '../../components/userstate/userstate.component';
import { map } from 'rxjs';
import { ImageProcessingService } from '../../services/image-processing/image-processing.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    NavbarComponent,
    FooterComponent,
    MiniAboutComponent,
    RouterLink,
    CategoryCardComponent,
    CommonModule,
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent{

  categoryItemList!: CategoryItem[];
  constructor(
    private categoryService: CategoryService, 
    private tokenService: TokenService,
    private imageProcessingService: ImageProcessingService
  ) {}

  ngOnInit(): void {
    
    this.tokenService.getTokenExpiration();
    this.getAllCategory();
  }

  getAllCategory(): void {
    this.categoryService.getAllCategory()
    .pipe(
      map((x: CategoryItem[], i) => x.map((product: CategoryItem) => this.imageProcessingService.createCategoryImage(product)))
    )
    .subscribe({
      next: (response) => {
        this.categoryItemList = response;
      },
      error: (error: string) => {
        console.log(`Error: ${error}`);
      },
    });
  }

  
}
