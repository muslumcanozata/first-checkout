# FIRST CHECKOUT

## E-commerce Shopping Cart Application

This project is a shopping cart application designed to accommodate various types of items and promotions. The application enforces specific rules and constraints for item management and promotions. Below, you will find detailed information about the key entities and actions within the application.

## Models

### Cart
- The Cart is an object that contains all items.
- All items are associated with a Cart.
- A Cart can have a maximum of 10 unique items (excluding VasItem).
- The total number of items in a Cart cannot exceed 30.
- The total price of items in a Cart cannot exceed 500,000 TL.

### Item
- Items represent products within the Cart.
- Items can be added to and removed from the Cart.
- Items can belong to various categories, such as VasItem, DefaultItem, and DigitalItem.
- Multiple items of the same type can be added to the Cart, with a maximum limit of 10 per item.
- Each item has a different price that is provided as input to the application.
- Items in the Cart have seller and category IDs.

### VasItem
- VasItems represent value-added services, such as insurance and assembly services.
- These items are associated with specific DefaultItems in the Furniture (CategoryID: 1001) and Electronics (CategoryID: 3004) categories.
- A maximum of 3 VasItems can be added to a DefaultItem.
- VasItems have a CategoryID of 3242 and a seller ID of 5003.
- No other item type shares the seller ID 5003.

### Promotion
- Promotions are entities that apply discounts to items in the Cart.
- Only one promotion can be applied to a Cart.
- If multiple promotions are eligible, the most advantageous promotion (providing the highest discount) is applied.

### CartItem
- CartItems are the items in the Cart.

### Seller
- Sellers are the entities that sell items.

[`GO TO SWAGGER-UI API DOCUMENTATION`](http://localhost:8080/api/v1/swagger-ui/)

## Action 1: Add Item
- This action allows you to add an item to the Cart.
- You need to provide the following details as input:
  - itemId: The unique identifier for the item.
  - categoryId: The category ID to which the item belongs.
  - sellerId: The seller ID associated with the item.
  - price: The price of the item.
  - quantity: The quantity of the item to add.
- The output will indicate whether the operation was successful (result) and provide a descriptive message (message).

**Input**
```json
{
  "itemId": int,
  "categoryId": int,
  "sellerId": int,
  "price": double,
  "quantity": int
}
```
**Output**
```json
{
  "result": boolean,
  "message": string

}
```

## Action 2: Add VasItem to Item
- This action allows you to add a Value Added Service (VasItem) to an existing item in the Cart.
- You need to provide the following details as input:
  - itemId: The unique identifier for the main item to which you want to add the VasItem.
  - vasItemId: The unique identifier for the VasItem.
  - categoryId: The category ID to which the VasItem belongs.
  - sellerId: The seller ID associated with the VasItem.
  - price: The price of the VasItem.
  - quantity: The quantity of the VasItem to add.
- The output will indicate whether the operation was successful (result) and provide a descriptive message (message).

**Input**
```json
{
  "itemId": int,
  "vasItemId": int,
  "categoryId": int,
  "sellerId": int,
  "price": double,
  "quantity": int
}
```
**Output**
```json
{
  "result": boolean,
  "message": string

}
```
## Action 3: Remove Item
- This action allows you to remove an item from the Cart.
- You need to provide the unique itemId of the item you want to remove as input.
- The output will indicate whether the operation was successful (result) and provide a descriptive message (message).

**Input**
```json
{
  "itemId": int //as path variable
}
```
**Output**
```json
{
  "result": boolean,
  "message": string
}
```

## Action 4: Reset Cart
- This action allows you to reset the Cart, removing all items and promotions.
- No input is required for this action.
- The output will indicate whether the operation was successful (result) and provide a descriptive message (message).

**Output**
```json
{
  "result": boolean,
  "message": string
}
```

## Action 5: Display Cart
- This action allows you to view the contents of the Cart along with applied promotions and discounts.
- No input is required for this action.
- The output will indicate whether the operation was successful (result) and provide detailed information about the items in the Cart, the total price, the applied promotion ID, and the total discount.

**Output**
```json
{
  "result": boolean,
  "message": {
    "items": [ty.item],
    "totalPrice": double,
    "appliedPromotionId": int,
    "totalDiscount": double
  }
}
```

**ty.item**
```json
{
  "itemId": int,
  "categoryId": int,
  "sellerId": int,
  "price": double,
  "quantity": int,
  "vasItems": [ty.vasItem]
}
```

**ty.vasItem**
```json
{
  "vasItemId": int,
  "categoryId": int,
  "sellerId": int,
  "price": double,
  "quantity": int
}
```

- ty.item represents the structure of items in the Cart.
- ty.vasItem represents the structure of Value Added Service (VasItem) items.


### Docker Compose support

This project contains a Docker Compose file named `compose.yaml`.
In this file, the following services have been defined:

* postgres: [`postgres:latest`](https://hub.docker.com/_/postgres)
* first-checkout [`first-checkout:0.0.1-SNAPSHOT`](https://github.com/DevelopmentHiring/MuslumCanOzata)

Checkout to dev branch:
```
$ git checkout dev
```
Copy .env.example to .env and set the environment variables:
```
$ cp env.example .env
```
To start the services, run:
```
$ docker-compose up -d
```
