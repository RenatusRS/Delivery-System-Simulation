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
