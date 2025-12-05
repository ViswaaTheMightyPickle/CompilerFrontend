1. All identifiers must start with _.
2. They must be either categorized as CONSTANTS or DYNAMIC.
3. Only the alphanumeric set is allowed.
4. Example:
   _a, _b, _MAX_TEMPERATURE (=> constant), _lives_lost (=> dynamic), etc.
1. num => obfuscation for int, long, long long, etc.
2. dec => obfuscation for double, float, etc.
3. character => 'a', etc.
3. null => empty type
4. bool => obfuscation for num; 0 or 1 only allowed, one bit of memory
5. {type}[_size] => array; static
6. {type}[dynamic] => array; dynamic
7. (Null arrays are invalid.)
8. {type} _VARIABLE_NAME <- 56; => makes it constant and immutable
9. Assigning: _c <- _a + _b;
2. if () then () ;
2. while () then () ;
3. for () then () ;
4. do () while () ;
5. foreach [_i in _array] then () ;
6. define {type} _function_name({type} _name, {type} name) then ( pull {value}; ) ;
7. class _class_name () ;
8. _class_name._function_name(_a, _b);
9. We'll be using UTF-8.
10. all comments must be placed between two '~'
11. " ", "\t", "\n", "\r" are all whitespace and we don't care about it unless its a seperator.
12. allowed digits -- 0 to 9.
13. leading zeros are not allowed unless you specify it like this:
    dec(leading) _a;
    dec(leading)[] _b;
14. use "." as decimal points.
15. exponential notation is allowed only like this:
    num(ex) _a;
    dec(leading|ex) _b; (=> to have both leading and exponents)
16. you can only use octal, hex, binary with num;
    num(xo) _octal;
    num(xh) _hex;
    num(xb) _binary;
18. underscores inside numbers isn't allowed.
19. use "" as delimiters for strings (character[_size] or character[dynamic]) and '' for characters.
20. "\n" as newline, "\t" as tab.
21. Any and all strings are character arrays that can be dynamic or static.
22. <, >, >=, <=, "equal" are the comparison operators.
23. logical operators are "and", "or", "not"
24. no bitwise.
25. math: +, -, *, /, %, ^.
26. punctuation: "()", "[]", "{}", ";", ",",
    

