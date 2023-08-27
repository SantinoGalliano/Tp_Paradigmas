module ModuloQuality (Quality, newQ, capacityQ, delayQ)
    where
 
data Quality = Qua String Int Float deriving (Eq, Show)

newQ :: String -> Int -> Float -> Quality
newQ nombre cantidadTuneles velocidad 
    | null nombre = error "El nombre de la calidad está vacio"
    | cantidadTuneles == 0 = error "La cantidad de tuneles debe ser mayor a cero"
    | velocidad == 0 = error "La velocidad debe ser mayor a cero"
    | otherwise = Qua nombre (abs cantidadTuneles) (abs velocidad)

capacityQ :: Quality -> Int 
capacityQ (Qua nombre cantidadTuneles velocidad) = cantidadTuneles

delayQ :: Quality -> Float
delayQ (Qua nombre cantidadTuneles velocidad) = velocidad