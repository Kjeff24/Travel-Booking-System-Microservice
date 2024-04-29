export interface FlightItem{
    id: string,
    departureCity: string,
    destinationCity: string,
    price: DoubleRange,
    date: Date,
    categoryId: string
}