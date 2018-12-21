package com.it.jwt.util;
      
    import java.util.HashMap;  
    import java.util.Map;  
      
    import com.auth0.jwt.JWTSigner;  
    import com.auth0.jwt.JWTVerifier;  
    import com.auth0.jwt.internal.com.fasterxml.jackson.databind.ObjectMapper;  
      
    public class JWT {
//        iss: jwt签发者
//        sub: jwt所面向的用户
//        aud: 接收jwt的一方
//        exp: jwt的过期时间，这个过期时间必须要大于签发时间
//        nbf: 定义在什么时间之前，该jwt都是不可用的.
//                iat: jwt的签发时间
//        jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。

        //  密钥secret是保存在服务端的，服务端会根据这个密钥进行生成token和验证，所以需要保护好。
        private static final String SECRET = "XX#$%()(#*!()!KL<><MQLMNQNQJQK sdfkjsdrow32234545fdf>?N<:{LWPW";
        //  exp: jwt的过期时间，这个过期时间必须要大于签发时间
        private static final String EXP = "exp";  
        //   负荷
        private static final String PAYLOAD = "payload";  
      
        /** 
         * 获取jwt对象的String
         * @param object 
         *            the POJO object 
         * @param maxAge 
         *            the milliseconds of life time 
         * @return the jwt token   灰色的波浪线表示同一个工程中有相同的方法名，但是不会报错，使用的时候注意选择对应的方法
         */  
        public static <T> String sign(T object, long maxAge) {  
            try {  
                final JWTSigner signer = new JWTSigner(SECRET);  
                final Map<String, Object> claims = new HashMap<String, Object>();  
                ObjectMapper mapper = new ObjectMapper();  
                String jsonString = mapper.writeValueAsString(object);  
                claims.put(PAYLOAD, jsonString);  
                claims.put(EXP, System.currentTimeMillis() + maxAge);  
                return signer.sign(claims);  
            } catch(Exception e) {  
                return null;  
            }  
        }  
          
          
        /** 
         * get the object of jwt if not expired
         * 如果未过期，则获取jwt的对象
         * @param jwt 
         * @return POJO object 
         */  
        public static<T> T unsign(String jwt, Class<T> classT) {  
            final JWTVerifier verifier = new JWTVerifier(SECRET);  
            try {  
                final Map<String,Object> claims= verifier.verify(jwt);  
                if (claims.containsKey(EXP) && claims.containsKey(PAYLOAD)) {  
                    long exp = (Long)claims.get(EXP);  
                    long currentTimeMillis = System.currentTimeMillis();  
                    if (exp > currentTimeMillis) {  
                        String json = (String)claims.get(PAYLOAD);  
                        ObjectMapper objectMapper = new ObjectMapper();  
                        return objectMapper.readValue(json, classT);  
                    }  
                }  
                return null;  
            } catch (Exception e) {  
                return null;  
            }  
        }  
    }  