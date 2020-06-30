
# mysql计算距离函数脚本

DELIMITER $$
CREATE FUNCTION `calculateLineDistance`(startLng double, startLat double, endLng double, endLat double) RETURNS double
BEGIN
declare d2 DOUBLE;
declare d3 DOUBLE;
declare d4 DOUBLE;
declare d5 DOUBLE;
declare d6 DOUBLE;
declare d7 DOUBLE;
declare d8 DOUBLE;
declare d9 DOUBLE;
declare d10 DOUBLE;
declare d11 DOUBLE;
declare d12 DOUBLE;
declare d13 DOUBLE;
declare d14 DOUBLE;
declare arrayOfDouble10 DOUBLE;
declare arrayOfDouble11 DOUBLE;
declare arrayOfDouble12 DOUBLe;
declare arrayOfDouble20 DOUBLE;
declare arrayOfDouble21 DOUBLE;
declare arrayOfDouble22 DOUBLE;
set d2 = startLng * 0.01745329251994329;
set d3 = startLat * 0.01745329251994329;
set d4 = endLng * 0.01745329251994329;
set d5 = endLat * 0.01745329251994329;
set d6 = sin(d2);
set d7 = sin(d3);
set d8 = cos(d2);
set d9 = cos(d3);
set d10 = sin(d4);
set d11 = sin(d5);
set d12 = cos(d4);
set d13 = cos(d5);
set arrayOfDouble10 = (d9 * d8);
set arrayOfDouble11 = (d9 * d6);
set arrayOfDouble12 = d7;
set arrayOfDouble20 = (d13 * d12);
set arrayOfDouble21 = (d13 * d10);
set arrayOfDouble22 = d11;
set d14 = sqrt((arrayOfDouble10 - arrayOfDouble20) * (arrayOfDouble10 - arrayOfDouble20)
            + (arrayOfDouble11 - arrayOfDouble21) * (arrayOfDouble11 - arrayOfDouble21)
            + (arrayOfDouble12 - arrayOfDouble22) * (arrayOfDouble12 - arrayOfDouble22));
return (asin(d14 / 2.0) * 12742001.579854401);
END $$
DELIMITER ;