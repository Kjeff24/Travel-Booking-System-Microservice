import { CartItem } from "./cart-item";

export class PaymentItem{
    id: string = '';
    cardId: string = '';
    email: string = '';
    exp_month: number = 0;
    exp_year: number = 0;
    last4: number = 0;
    totalCost: number = 0;
    userId: string = '';
    cartItems: CartItem[];
    paymentDate: Date;

    constructor(){}
}