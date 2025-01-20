# Workshop Cleaner Architecture

## Summary

## The demo application - an eCommerce solution named 'Colossus'
As we know, the primary objective of a software is to fulfill some given requirements...
Therefore - throughout the workshop - we will learn, exercise and train the learned material in the context of a "real" world example.
We choose the example of an eCommerce solution: we call our solution 'Colossus'.

### The usecases
The usecases we focus on are:
<ol>Catalog-Management
  <li>Add/Update/Delete Product</li>
  <li>Add/Update/Delete Product-Category</li>
</ol>
<ol>Cart-Management
  <li>Add/Remove Product in Cart</li>
  <li>...</li>
</ol>

### The general data-model
The data-model we use consist of the following main objects:

```mermaid
  classDiagram
      class Product { 
      -id: long
      -name : string
      -summary: text
      -details: text        
      }
      class ProductCategory { 
      -id: long
      -name : string
      }
      class ProductSpecification { 
      -id: long
      -description : string
      }      
      class ProductImage { 
      -id: long
      -image : byte[]
      -isMain : bool
      }
      class Cart { 
      -sessionId: string
      }
      Product "1" *-- "1" ProductSpecification : has
      Product "1" -- "1" ProductCategory : references
      Product "1" *-- "*" ProductImage : has
      Catalog "1" o-- "*" Product : has      
      Catalog "1" *-- "*" ProductCategory : has        
```
### The overall architecture
The architecture in which the eCommerce-solution is embedded is as follows::

```mermaid
C4Context
      Enterprise_Boundary(b0, "Actors") {
        Person(customer, "Customer", "A customer of OurCompany - using typically our eCommerce-solution.")
        Person(eCommerceAdmin, "eCommerce Administrator")        
      }
      
      Enterprise_Boundary(b1, "Clients / Browser") {
        System(colossusFrontend, "Colossus Frontend (e-Commerce)", "Allows customers to browse the product catalog and to purchase products.")
      }
      
      Enterprise_Boundary(b2, "OurCompany") {
        Enterprise_Boundary(b21, "Container-Platform") {            
            System_Boundary(b211, "Web-Access") {
                System(apiGW, "API Gateway")
            }
            System_Boundary(b212, "Colossus-Backends (e-Commerce)") {
                System(colossusBackend, "Colossus Backend", "Allows customers to browse the product catalog and to purchase products.")
                SystemDb_Ext(colossusDB, "Colossus DB", "Stores all products and the information relevant to the customer's shoppingcarts.")
            }            
        }
        
        Enterprise_Boundary(b22, "Core-Systems") {
          SystemDb_Ext(erpSystem, "ERP System", "Stores all of the core enterprise information about customers, accounts, transactions, etc.")
          System_Ext(emailSystem, "E-mail system", "The internal Microsoft Exchange e-mail system.")
        }
      }

      BiRel(customer, colossusFrontend, "Uses")
      BiRel(eCommerceAdmin, colossusFrontend, "Uses")
      Rel(colossusFrontend, apiGW, "")
      Rel(apiGW, colossusBackend, "")
      Rel(colossusFrontend, colossusBackend, "Uses")
      Rel(colossusBackend, colossusDB, "Uses")
      Rel(colossusBackend, emailSystem, "Uses")

      UpdateElementStyle(customer, $fontColor="black", $bgColor="lightgrey", $borderColor="black")
      UpdateElementStyle(eCommerceAdmin, $fontColor="black", $bgColor="lightgrey", $borderColor="black")
      UpdateElementStyle(colossusFrontend, $fontColor="white", $bgColor="blue", $borderColor="black")
      UpdateElementStyle(colossusBackend, $fontColor="white", $bgColor="blue", $borderColor="black")
      UpdateElementStyle(apiGW, $fontColor="white", $bgColor="grey", $borderColor="black")
      UpdateElementStyle(colossusDB, $fontColor="white", $bgColor="blue", $borderColor="black")
      
      UpdateRelStyle(customer, colossusFrontend, $textColor="blue", $lineColor="black", $offsetX="5")
      UpdateRelStyle(colossusFrontend, apiGW, $textColor="blue", $lineColor="black", $offsetY="-10")
      UpdateRelStyle(apiGW, colossusBackend, $textColor="blue", $lineColor="black", $offsetY="-40", $offsetX="-50")
      UpdateRelStyle(colossusBackend, eCommerceDB, $textColor="blue", $lineColor="black", $offsetX="-50", $offsetY="20")
      UpdateRelStyle(colossusBackend, emailSystem, $textColor="blue", $lineColor="black", $offsetX="-50", $offsetY="20")

      UpdateLayoutConfig($c4ShapeInRow="3", $c4BoundaryInRow="1")
```

### The components
<ol>
  <li>[Colossus-Backend-Service](./colossus/backend/README-md)</li>
</ol>
