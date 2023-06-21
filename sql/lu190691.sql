
CREATE TABLE [Article]
( 
	[ArticleID]          integer  IDENTITY  NOT NULL ,
	[Name]               varchar(100)  NOT NULL ,
	[Price]              integer  NOT NULL ,
	[Count]              integer  NOT NULL ,
	[ShopID]             integer  NOT NULL 
)
go

CREATE TABLE [Buyer]
( 
	[BuyerID]            integer  IDENTITY  NOT NULL ,
	[Name]               varchar(100)  NOT NULL ,
	[Credit]             decimal(10,3)  NOT NULL ,
	[CityID]             integer  NOT NULL 
)
go

CREATE TABLE [City]
( 
	[CityID]             integer  IDENTITY  NOT NULL ,
	[Name]               varchar(100)  NOT NULL 
)
go

CREATE TABLE [Connection]
( 
	[ConnectionID]       integer  IDENTITY  NOT NULL ,
	[CityID1]            integer  NOT NULL ,
	[CityID2]            integer  NOT NULL ,
	[Distance]           integer  NOT NULL 
)
go

CREATE TABLE [Order]
( 
	[OrderID]            integer  IDENTITY  NOT NULL ,
	[Status]             varchar(7)  NOT NULL ,
	[BuyerID]            integer  NOT NULL ,
	[SentTime]           datetime  NULL ,
	[RecievedTime]       datetime  NULL ,
	[Distance]           integer  NOT NULL ,
	[CurrentCityID]      integer  NULL ,
	[TargetCityID]       integer  NULL 
)
go

CREATE TABLE [OrderArticle]
( 
	[OrderArticleID]     integer  IDENTITY  NOT NULL ,
	[ArticleID]          integer  NOT NULL ,
	[OrderID]            integer  NOT NULL ,
	[Count]              integer  NOT NULL ,
	[ShopCityID]         integer  NULL ,
	[TargetCityID]       integer  NULL ,
	[Distance]           integer  NOT NULL ,
	[Cost]               float  NULL 
)
go

CREATE TABLE [Shop]
( 
	[ShopID]             integer  IDENTITY  NOT NULL ,
	[CityID]             integer  NOT NULL ,
	[Name]               varchar(100)  NOT NULL ,
	[Discount]           integer  NOT NULL 
)
go

CREATE TABLE [Transaction]
( 
	[TransactionID]      integer  NOT NULL ,
	[Amount]             decimal(10,3)  NOT NULL ,
	[OrderID]            integer  NOT NULL ,
	[BuyerID]            integer  NULL ,
	[ShopID]             integer  NULL ,
	[ExecutionTime]      datetime  NULL 
)
go

ALTER TABLE [Article]
	ADD CONSTRAINT [XPKArticle] PRIMARY KEY  CLUSTERED ([ArticleID] ASC)
go

ALTER TABLE [Buyer]
	ADD CONSTRAINT [XPKBuyer] PRIMARY KEY  CLUSTERED ([BuyerID] ASC)
go

ALTER TABLE [City]
	ADD CONSTRAINT [XPKCity] PRIMARY KEY  CLUSTERED ([CityID] ASC)
go

ALTER TABLE [Connection]
	ADD CONSTRAINT [XPKConnection] PRIMARY KEY  CLUSTERED ([ConnectionID] ASC)
go

ALTER TABLE [Order]
	ADD CONSTRAINT [XPKOrder] PRIMARY KEY  CLUSTERED ([OrderID] ASC)
go

ALTER TABLE [OrderArticle]
	ADD CONSTRAINT [XPKOrderArticle] PRIMARY KEY  CLUSTERED ([OrderArticleID] ASC)
go

ALTER TABLE [Shop]
	ADD CONSTRAINT [XPKShop] PRIMARY KEY  CLUSTERED ([ShopID] ASC)
go

ALTER TABLE [Transaction]
	ADD CONSTRAINT [XPKTransaction] PRIMARY KEY  CLUSTERED ([TransactionID] ASC)
go

ALTER TABLE [Article]
	ADD CONSTRAINT [ZERO_1855560304]
		 DEFAULT  0 FOR [Count]
go


ALTER TABLE [Article]
	ADD CONSTRAINT [R_13] FOREIGN KEY ([ShopID]) REFERENCES [Shop]([ShopID])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Buyer]
	ADD CONSTRAINT [ZERO_626947247]
		 DEFAULT  0 FOR [Credit]
go


ALTER TABLE [Buyer]
	ADD CONSTRAINT [R_11] FOREIGN KEY ([CityID]) REFERENCES [City]([CityID])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Connection]
	ADD CONSTRAINT [R_8] FOREIGN KEY ([CityID1]) REFERENCES [City]([CityID])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Connection]
	ADD CONSTRAINT [R_9] FOREIGN KEY ([CityID2]) REFERENCES [City]([CityID])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Order]
	ADD CONSTRAINT [Order_State_636124248]
		CHECK  ( [Status]='created' OR [Status]='sent' OR [Status]='arrived' ) 
go

ALTER TABLE [Order]
	ADD CONSTRAINT [ZERO_1921093354]
		 DEFAULT  0 FOR [Distance]
go


ALTER TABLE [Order]
	ADD CONSTRAINT [R_14] FOREIGN KEY ([BuyerID]) REFERENCES [Buyer]([BuyerID])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Order]
	ADD CONSTRAINT [R_31] FOREIGN KEY ([CurrentCityID]) REFERENCES [City]([CityID])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Order]
	ADD CONSTRAINT [R_32] FOREIGN KEY ([TargetCityID]) REFERENCES [City]([CityID])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [OrderArticle]
	ADD CONSTRAINT [ZERO_724855912]
		 DEFAULT  0 FOR [Distance]
go


ALTER TABLE [OrderArticle]
	ADD CONSTRAINT [R_20] FOREIGN KEY ([ArticleID]) REFERENCES [Article]([ArticleID])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [OrderArticle]
	ADD CONSTRAINT [R_21] FOREIGN KEY ([OrderID]) REFERENCES [Order]([OrderID])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [OrderArticle]
	ADD CONSTRAINT [R_27] FOREIGN KEY ([ShopCityID]) REFERENCES [City]([CityID])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [OrderArticle]
	ADD CONSTRAINT [R_30] FOREIGN KEY ([TargetCityID]) REFERENCES [City]([CityID])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Shop]
	ADD CONSTRAINT [ZERO_1636606871]
		 DEFAULT  0 FOR [Discount]
go


ALTER TABLE [Shop]
	ADD CONSTRAINT [R_10] FOREIGN KEY ([CityID]) REFERENCES [City]([CityID])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Transaction]
	ADD CONSTRAINT [R_23] FOREIGN KEY ([OrderID]) REFERENCES [Order]([OrderID])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Transaction]
	ADD CONSTRAINT [R_24] FOREIGN KEY ([BuyerID]) REFERENCES [Buyer]([BuyerID])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Transaction]
	ADD CONSTRAINT [R_25] FOREIGN KEY ([ShopID]) REFERENCES [Shop]([ShopID])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


USE [SAB2223]
GO

/****** Object:  StoredProcedure [dbo].[spMoreDiscount]    Script Date: 21.6.2023. 07:22:44 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[spMoreDiscount]
@Date DATE,
@BuyerId INT

AS

BEGIN
	DECLARE @Cursor CURSOR;
	DECLARE @ExecutionTime DATE;
	DECLARE @days INT;

	SET @Cursor = CURSOR FOR
		SELECT ExecutionTime FROM [Transaction]
		WHERE BuyerID = @BuyerId AND Amount >= 10000

	OPEN @Cursor
	FETCH NEXT FROM @Cursor INTO @ExecutionTime

	WHILE @@FETCH_STATUS = 0
	BEGIN
		SET @days = DATEDIFF(day, @ExecutionTime, @Date)

		IF (@days <= 30)
		BEGIN
			CLOSE @Cursor
			DEALLOCATE @Cursor
			RETURN 1
		END

		FETCH NEXT FROM @Cursor INTO @ExecutionTime
	END

	CLOSE @Cursor
	DEALLOCATE @Cursor
	RETURN 0
END
GO

USE [SAB2223]
GO

/****** Object:  Trigger [dbo].[sendMoneyToShops]    Script Date: 21.6.2023. 07:21:43 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TRIGGER [dbo].[TR_TRANSFER_MONEY_TO_SHOPS]
ON [dbo].[Order]
AFTER UPDATE

AS

BEGIN
	DECLARE @Cursor CURSOR;

	SET @Cursor = CURSOR FOR
		SELECT OrderID, RecievedTime
		FROM inserted
		WHERE [Status] = 'arrived';

	OPEN @Cursor;

	DECLARE @OrderId INTEGER;
	DECLARE @ExecutionTime DATE;

	FETCH NEXT FROM @Cursor INTO @OrderId, @ExecutionTime;

	SET @Cursor = CURSOR FOR
		SELECT ArticleID, Cost
		FROM OrderArticle
		WHERE OrderID = @OrderId;

	OPEN @Cursor;

	DECLARE @Cost FLOAT;
	DECLARE @ArticleID INTEGER;
	DECLARE @CursorArticle CURSOR;

	DECLARE @ShopID INTEGER;

	FETCH NEXT FROM @Cursor INTO @ArticleID, @Cost;

	WHILE @@FETCH_STATUS = 0
	BEGIN
		SET @CursorArticle = CURSOR FOR
			SELECT ShopID
			FROM [Article]
			WHERE ArticleID = @ArticleID;

		OPEN @CursorArticle;

		FETCH NEXT FROM @CursorArticle INTO @ShopID;

		CLOSE @CursorArticle;
		DEALLOCATE @CursorArticle;

		INSERT INTO [Transaction] (Amount, OrderID, ShopID, ExecutionTime) VALUES (@Cost * 0.95, @OrderId, @ShopID, @ExecutionTime);
		
		FETCH NEXT FROM @Cursor INTO @ArticleID, @Cost;
	END


	CLOSE @Cursor

	DEALLOCATE @Cursor
END

GO

ALTER TABLE [dbo].[Order] ENABLE TRIGGER [TR_TRANSFER_MONEY_TO_SHOPS]
GO




USE [SAB2223]
GO

/****** Object:  StoredProcedure [dbo].[SP_FINAL_PRICE]    Script Date: 21.6.2023. 07:22:29 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[SP_FINAL_PRICE]
@OrderId INT,
@Date DATE
AS

BEGIN
	DECLARE @Cursor CURSOR;
	DECLARE @CursorArticle CURSOR;
	DECLARE @CursorShop CURSOR;
	DECLARE @CursorOrder CURSOR;


	SET @Cursor = CURSOR FOR
		SELECT ArticleId, [Count] FROM [OrderArticle]
		WHERE OrderId = @OrderId

	OPEN @Cursor

	DECLARE @total INTEGER;

	SET @total = 0;

	DECLARE @ArticleId INT;
	DECLARE @Count INT;

	DECLARE @Price FLOAT;

	DECLARE @ShopId INT;
	DECLARE @Discount FLOAT;

	FETCH NEXT FROM @Cursor INTO @ArticleId, @Count
	WHILE @@FETCH_STATUS = 0
	BEGIN
		SET @CursorArticle = CURSOR FOR
			SELECT ShopId, Price FROM Article
			WHERE ArticleId = @ArticleId

		OPEN @CursorArticle

		FETCH NEXT FROM @CursorArticle INTO @ShopId, @Price;

		SET @CursorShop = CURSOR FOR
			SELECT Discount FROM Shop
			WHERE ShopId = @ShopId

		OPEN @CursorShop

		FETCH NEXT FROM @CursorShop INTO @Discount

		SET @total = @total + (@Count * @Price) * (100 - @Discount) / 100;

		FETCH NEXT FROM @Cursor INTO @ArticleId, @Count
	END

	DECLARE @AdditionalDiscount INTEGER;

	DECLARE @BuyerId INTEGER;

	SET @CursorOrder = CURSOR FOR
		SELECT BuyerId FROM [Order]
		WHERE OrderId = @OrderId

	OPEN @CursorOrder

	FETCH NEXT FROM @CursorOrder INTO @BuyerId

	EXEC @AdditionalDiscount = [spMoreDiscount] @Date, @BuyerId

	IF (@AdditionalDiscount = 1)
	BEGIN
		SET @total = @total * 0.98
	END

	CLOSE @Cursor
	CLOSE @CursorArticle
	CLOSE @CursorShop
	CLOSE @CursorOrder

	DEALLOCATE @Cursor
	DEALLOCATE @CursorArticle
	DEALLOCATE @CursorShop
	DEALLOCATE @CursorOrder

	RETURN @total
END
GO
