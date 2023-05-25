package com.sample.demo.gpb.test;

/**
 * Created by sbose on 29/3/23.
 */
public class TestGPB {

    /*public static void maind(String[] args) throws Exception {
        GpbPerson.PersonRequestV1 personRequestV1 =  GpbPerson.PersonRequestV1.newBuilder()
                .setEmail("test@test.com")
                .setAge(2)
                .setName("test name")
                .build();
        byte[] data = personRequestV1.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/x-protobuf");
        headers.set("Content-Type", "application/x-protobuf");
        HttpEntity<byte[]> entity = new HttpEntity<>(data,headers);


        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<byte[]> response = restTemplate.exchange("http://localhost:8080/person", HttpMethod.POST,
                entity, byte[].class);
        GpbPerson.PersonResponseV1 personResponseV1 = GpbPerson.PersonResponseV1.parseFrom(response.getBody());
        System.out.println(personResponseV1.getMessage());
    }

    public static void main(String[] args) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/x-protobuf");
        headers.set("Content-Type", "application/x-protobuf");
        HttpEntity<byte[]> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<byte[]> response = restTemplate.exchange("http://localhost:8080/person", HttpMethod.GET,
                entity, byte[].class);
        GpbPerson.PersonResponseV1 personResponseV1 = GpbPerson.PersonResponseV1.parseFrom(response.getBody());
        System.out.println(personResponseV1.getEmail() + " : " + personResponseV1.getMessage());
    }*/
}
