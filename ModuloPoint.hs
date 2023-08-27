module ModuloPoint (Point, newP, difP)
      where

data Point = Poi Int Int deriving (Eq, Show)

newP :: Int -> Int -> Point
newP pos_x pos_y  = Poi pos_x pos_y

difP :: Point -> Point -> Float  
difP (Poi x1 y1) (Poi x2 y2) = sqrt(fromIntegral ((x2 - x1)^2 + (y2 - y1)^2))