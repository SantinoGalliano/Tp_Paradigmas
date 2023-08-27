module ModuloLink (Link, newL, connectsL, linksL, delayL, capacityL)
    where

import ModuloQuality
import ModuloCity
import ModuloPoint
import Errores
data Link = Lin City City Quality deriving (Eq, Show)

newL :: City -> City -> Quality -> Link
newL ciudad1 ciudad2 calidad 
  | ciudadesRepetidas ciudad1 ciudad2 = error "No se puede hacer un link entre dos ciudades semejantes"
  | otherwise = Lin ciudad1 ciudad2 calidad

connectsL :: City -> Link -> Bool
connectsL ciudad (Lin ciudad1 ciudad2 calidad) = ciudad `elem` [ciudad1, ciudad2]

linksL :: City -> City -> Link -> Bool
linksL ciudad1 ciudad2 (Lin c1 c2 velocidad) 
  | ciudadesRepetidas ciudad1 ciudad2 = False
  | otherwise = (ciudad1, ciudad2) `elem` [(c1, c2), (c2, c1)]

capacityL :: Link -> Int
capacityL (Lin ciudad1 ciudad2 calidad) = capacityQ calidad

delayL :: Link -> Float
delayL (Lin ciudad1 ciudad2 calidad) = distanceC ciudad1 ciudad2 * delayQ calidad

ciudadesRepetidas :: City -> City -> Bool

ciudadesRepetidas ciudad1 ciudad2 
       | ciudad1 == ciudad2 = True
       | distanceC ciudad1 ciudad2 == 0 = True
       | nameC ciudad1 == nameC ciudad2 = True
       | otherwise = False

--testing de ciudadesRepetidas
ciudad0 = newC "olivos" (newP (-3) 4)
ciudad1 = newC "olivos" (newP (-3) 4)
ciudad2 = newC "martinez" (newP 8 10)
ciudad4 = newC "munro" (newP 7 7)

ciudad5 = newC "martinez" (newP (-3) 4)
ciudad6 = newC "munro" (newP 4 3)

ciudadesIguales = ciudadesRepetidas ciudad0 ciudad1 -- devuelve True si las ciudades son iguales
ciudadesConMismaUbicacion = ciudadesRepetidas ciudad0 ciudad5 -- devuelve True si las ciudades tienen la misma ubicación

ciudadesConMismoNombre = ciudadesRepetidas ciudad4 ciudad6 -- devuelve True si las ciudades tienen el mismo nombre

ciudadesDistintas = ciudadesRepetidas ciudad5 ciudad6 -- devuelve False si las ciudades son distintas