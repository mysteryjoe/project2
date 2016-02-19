I am Yunwen Zhu, Uid 104593417


I use database as folow:
Entity Table:
user(ID,Location,Country,SellingRating,BidRating), where ID is the primary key.
item(ID, Name, Currently, Buy_Price, First_Bid, Number_of_Bids, Location, Latitude, Longitude, Country, Started, Ends, Seller, Description) where ID is the primary key
Category(ID,type), where ID is primary key

Relation Table:
bid(Iid,Uid,Time,Amount) where Iid references item.id, Uid references user.id;
belong(Iid,Cid) where Iid references item.ID, Cid references Category.ID;

FDs and MVDs:
bid.id -> Location, Country, SellRating, BidRating
item.ID -> Name, Currently, Buy_Price, First_Bid, Number_of_Bids, Location, Latitude, Longitiude, Country, Started, Ends, Seller, Description
Iid, Uid, Time -> Amount

item.ID ->-> category
I made the decomposition that there is no BCNF or 4NF violation on each table