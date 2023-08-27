module ModuloCity (City, newC, nameC, distanceC)
   where

import ModuloPoint ( difP, newP, Point )

data City = Cit String Point deriving (Eq, Show)

newC :: String -> Point -> City
newC nombreDeCiudad ubicacion 
   | null nombreDeCiudad = error "El nombre de la ciudad no puede estar vacío" 
   | otherwise = Cit nombreDeCiudad ubicacion

nameC :: City -> String
nameC (Cit nombreDeCiudad ubicacion) = nombreDeCiudad

distanceC :: City -> City -> Float
distanceC (Cit nombreDeCiudad1 ubicacion1) (Cit nombreDeCiudad2 ubicacion2)
 | ciudadesRepetidas ciudad1 ciudad2 = error "No se puede calcular la distancia entre ciudades semejantes"
 | otherwise = difP ubicacion1 ubicacion2 
      where ciudad1 = Cit nombreDeCiudad1 ubicacion1
            ciudad2 = Cit nombreDeCiudad2 ubicacion2
          
ciudadesRepetidas :: City -> City -> Bool

ciudadesRepetidas (Cit nombreCiudad1 ubicacion1) (Cit nombreCiudad2 ubicacion2)
       | ciudad1 == ciudad2 = True
       | nameC ciudad1 == nameC ciudad2 = True
       | ubicacion1 == ubicacion2 = True
       | otherwise = False
         where ciudad1 = (Cit nombreCiudad1 ubicacion1)
               ciudad2 = (Cit nombreCiudad2 ubicacion2)

-- testing de ciudadesRepetidas
                
ciudad0 = newC "olivos" (newP (-3) 4)
ciudad1 = newC "olivos" (newP (-3) 4)
ciudad2 = newC "martinez" (newP 8 10)
ciudad4 = newC "munro" (newP 7 7)

ciudad5 = newC "martinez" (newP (-3) 4)
ciudad6 = newC "munro" (newP 4 3)

ciudadesIguales = ciudadesRepetidas ciudad0 ciudad1 -- devuelve True si las ciudades son iguales
ciudadesConMismaUbicacion = ciudadesRepetidas ciudad0 ciudad5 -- devuelve True si las ciudades tienne la misma ubicación

ciudadesConMismoNombre = ciudadesRepetidas ciudad4 ciudad6 -- devuelve True si las ciudades tienen el mismo nombre

ciudadesDistintas = ciudadesRepetidas ciudad5 ciudad6 -- devuelve False si las ciudades son distintas