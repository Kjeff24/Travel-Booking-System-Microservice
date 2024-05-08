import { Component } from '@angular/core';
import { PaymentService } from '../../../services/payment/payment.service';
import { CommonModule } from '@angular/common';
import { PaymentItem } from '../../../models/payment-item';

@Component({
  selector: 'app-transaction',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './transaction.component.html',
  styleUrl: './transaction.component.css'
})
export class TransactionComponent {
  paymentItemList: PaymentItem[] = [];

  constructor(
    private paymentService: PaymentService
  ){
  }

  ngOnInit(): void {
    this.paymentService.getAllPayments().subscribe({
      next: (response) => {
          this.paymentItemList = response.body;
      },
      error: (err) => {
          console.error('Error while fetching transactions');
      },
    })
  }

  deletePayment(paymentId): void {
    this.paymentService.deletePaymentById(paymentId).subscribe({
      next: () => {
        console.log("Payment deletion successful");
        window.location.reload();
      },
      error: (err) => {
          console.log(err);
      },
    })
  }

}
