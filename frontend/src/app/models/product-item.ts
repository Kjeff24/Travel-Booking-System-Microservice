export interface ProductItem {
	discount: number;
	images: {
		firstView: string;
		hoverView: string;
	};
	category: string;
	title: string;
	stars: number;
	reviewCount: number;
	oldPrice: number;
	newPrice: number;
}
