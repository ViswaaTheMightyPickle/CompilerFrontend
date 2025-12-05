First, we need some key rules for identifiers.
1. All identifiers must start with _.
2. They must be either categorized as CONSTANTS or DYNAMIC.
3. Only the alphanumeric set is allowed.
4. Example:
   _a, _b, _MAX_TEMPERATURE (=> constant), _lives_lost (=> dynamic), etc.

Then we'll need keywords.
1. num => obfuscation for int, long, long long, etc.
2. dec => obfuscation for double, float, etc.
3. null => empty type
4. bool => obfuscation for num; 0 or 1 only allowed, one bit of memory
5. {type}[] => array; static
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

