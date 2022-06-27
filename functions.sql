DROP FUNCTION UserPoints;
DROP FUNCTION UserPointsCat;
DROP FUNCTION UserResolvedCtfs;
DROP FUNCTION UserResolvedCtfsCat;
DROP FUNCTION FirstResolver;
DROP FUNCTION NumberResolver;
DROP FUNCTION NumberAttempts;
DROP FUNCTION UserAttemptedCtf;
DROP FUNCTION NumberFirstResolver;
DROP FUNCTION AlreadyResolved;
DROP FUNCTION AlreadyAttempted;

DELIMITER $$
CREATE FUNCTION UserPoints(name VARCHAR(50))
RETURNS INTEGER DETERMINISTIC
BEGIN
	DECLARE points INTEGER;

	SELECT sum(CTF.difficolta)*10 INTO points 
	FROM Risolte, CTF
	WHERE Risolte.utente = name AND
		  Risolte.ctf = CTF.id;

	SET points = points + NumberFirstResolver(name) * 25;

	IF ISNULL(points) THEN
		RETURN 0;
	ELSE
		RETURN points;
	END IF;
END
$$
DELIMITER ;

DELIMITER $$
CREATE FUNCTION UserPointsCat(name VARCHAR(50), category ENUM("Binary Exploitation", "Web Exploitation", "Reverse Engineering", "Forensic"))
RETURNS INTEGER DETERMINISTIC
BEGIN
	DECLARE points INTEGER;
	
	SELECT sum(CTF.difficolta)*10 INTO points 
	FROM Risolte, CTF
	WHERE Risolte.utente = name AND
		  Risolte.ctf = CTF.id AND
		  CTF.categoria = category;
	
	IF ISNULL(points) THEN
		RETURN 0;
	ELSE
		RETURN points;
	END IF;
END
$$
DELIMITER ;

DELIMITER $$
CREATE FUNCTION UserResolvedCtfs(name VARCHAR(50))
RETURNS INTEGER DETERMINISTIC
BEGIN
	DECLARE n INTEGER;
	
	SELECT count(*) INTO n 
	FROM Risolte, CTF
	WHERE Risolte.utente = name AND
		  Risolte.ctf = CTF.id;
	
	IF ISNULL(n) THEN
		RETURN 0;
	ELSE
		RETURN n;
	END IF;
END
$$
DELIMITER ;

DELIMITER $$
CREATE FUNCTION UserResolvedCtfsCat(name VARCHAR(50), category ENUM("Binary Exploitation", "Web Exploitation", "Reverse Engineering", "Forensic"))
RETURNS INTEGER DETERMINISTIC
BEGIN
	DECLARE n INTEGER;
	
	SELECT count(*) INTO n 
	FROM Risolte, CTF
	WHERE Risolte.utente = name AND
		  Risolte.ctf = CTF.id AND
		  CTF.categoria = category;
	
	IF ISNULL(n) THEN
		RETURN 0;
	ELSE
		RETURN n;
	END IF;
END
$$
DELIMITER ;

DELIMITER $$
CREATE FUNCTION UserAttemptedCtf(name VARCHAR(50))
RETURNS INTEGER DETERMINISTIC
BEGIN
	DECLARE n INTEGER;
	
	SELECT count(*) INTO n 
	FROM Prova, CTF
	WHERE Prova.utente = name AND
		  Prova.ctf = CTF.id;
	
	IF ISNULL(n) THEN
		RETURN 0;
	ELSE
		RETURN n;
	END IF;
END
$$
DELIMITER ;

DELIMITER $$
CREATE FUNCTION FirstResolver(ctf INTEGER)
RETURNS VARCHAR(50) DETERMINISTIC
BEGIN
	DECLARE user VARCHAR(50);
	
	SELECT Risolte.utente INTO user 
	FROM  CTF, Risolte
	WHERE CTF.id = ctf AND
	      Risolte.ctf = CTF.id
	ORDER BY Risolte.ts
	LIMIT 1; 
	
	RETURN user;
END
$$
DELIMITER ;


DELIMITER $$
CREATE FUNCTION NumberFirstResolver(name VARCHAR(50))
RETURNS INTEGER DETERMINISTIC
BEGIN
	DECLARE n INTEGER;
	
	SELECT count(*) INTO n
	FROM  CTF, Risolte
	WHERE Risolte.utente = name AND
	      Risolte.ctf = CTF.id AND
	      FirstResolver(CTF.id) = name;
	
	RETURN n;
END
$$
DELIMITER ;

DELIMITER $$
CREATE FUNCTION NumberResolver(ctf INTEGER)
RETURNS INTEGER DETERMINISTIC
BEGIN
	DECLARE n INTEGER;
	
	SELECT count(*) INTO n 
	FROM  CTF, Risolte
	WHERE CTF.id = ctf AND
	      Risolte.ctf = CTF.id;
	
	RETURN n;
END
$$
DELIMITER ;

DELIMITER $$
CREATE FUNCTION NumberAttempts(ctf INTEGER)
RETURNS INTEGER DETERMINISTIC
BEGIN
	DECLARE n INTEGER;
	
	SELECT count(*) INTO n 
	FROM  CTF, Prova
	WHERE CTF.id = ctf AND
	      Prova.ctf = CTF.id;
	
	RETURN n;
END
$$
DELIMITER ;

DELIMITER $$
CREATE FUNCTION AlreadyResolved(ctf INTEGER, utente VARCHAR(50))
RETURNS BOOLEAN DETERMINISTIC
BEGIN
	DECLARE n INTEGER;
	
	SELECT CTF.id INTO n
	FROM  CTF, Risolte
	WHERE CTF.id = ctf AND
	      Risolte.ctf = CTF.id AND
	      Risolte.utente = utente;

	IF ISNULL(n) THEN
		RETURN FALSE;
	ELSE
		RETURN TRUE;
	END IF;
END
$$
DELIMITER ;

DELIMITER $$
CREATE FUNCTION AlreadyAttempted(ctf INTEGER, utente VARCHAR(50))
RETURNS BOOLEAN DETERMINISTIC
BEGIN
	DECLARE n INTEGER;
	
	SELECT CTF.id INTO n
	FROM  CTF, Prova
	WHERE CTF.id = ctf AND
	      Prova.ctf = CTF.id AND
	      Prova.utente = utente;

	IF ISNULL(n) THEN
		RETURN FALSE;
	ELSE
		RETURN TRUE;
	END IF;
END
$$
DELIMITER ;
