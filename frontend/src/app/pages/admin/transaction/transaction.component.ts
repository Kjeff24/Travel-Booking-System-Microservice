import { Component } from '@angular/core';
import { PaymentService } from '../../../services/payment/payment.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-transaction',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './transaction.component.html',
  styleUrl: './transaction.component.css'
})
export class TransactionComponent {
  paymentItemList: PaymentItem[];

  constructor(
    private paymentService: PaymentService
  ){}

  ngOnInit(): void {
    this.paymentService.getAllPayments().subscribe({
      next(response) {
          this.paymentItemList = response.body
          console.log(this.paymentItemList)
      },
      error(err) {
          console.error('Error while fetching transactions');
      },
    })
  }

}
