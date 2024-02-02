use japanese_restaurant

--c. 2 queries with the difference operation; use EXCEPT and NOT IN;
--EXCEPT operation ->Get the ingredients that cost less than 50Ron and are bought in less than 70 pieces
(SELECT I.id_ingredient, I.description_i as Ingredient_informations
FROM ingredient I,ingredient_distribuiton IngrD
WHERE I.id_ingredient=IngrD.id_ingredient and IngrD.price<50)
EXCEPT
(SELECT I.id_ingredient, I.description_i
FROM ingredient I,ingredient_distribuiton IngrD
WHERE I.id_ingredient=IngrD.id_ingredient AND IngrD.quantity > 70)

--NOT IN operation ->Get the ingredients that are not used in the products named 'classic sushi rolls'
SELECT I.id_ingredient, I.description_i as Ingredient_informations
FROM ingredient I, recipe R, product P
WHERE i.id_ingredient = R.id_ingredient AND R.id_product=P.id_product AND I.description_i NOT IN
	(SELECT I.description_i
	FROM ingredient I, recipe R, product P
	WHERE i.id_ingredient = R.id_ingredient AND R.id_product=P.id_product AND P.name_pr = 'classic sushi rolls')