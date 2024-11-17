INSERT INTO "mercado_pago_gateways" ("id", "collectors", "external_pos")
VALUES ('773f0c86-96a1-467e-af8b-184c2994f9ac', '1650421194', 'SUC001POS001')
ON CONFLICT ("id") DO NOTHING;

INSERT INTO stores (id, name, active, id_mercado_pago_gateway)
VALUES ('e1defa99-5e85-4b34-906e-145b1f42bd57', 'FIAP FOOD', true, '773f0c86-96a1-467e-af8b-184c2994f9ac')
ON CONFLICT ("id") DO NOTHING;

INSERT INTO customers (id, name, email, cpf, id_store)
VALUES ('fac9e733-5d52-4918-a2f1-e2fc6a7487f1', 'Jose Vitor', 'jose.vitor@getnada.com', '58743120059',
        'e1defa99-5e85-4b34-906e-145b1f42bd57')
ON CONFLICT ("id") DO NOTHING;

INSERT INTO categories (id, name, id_store)
VALUES ('d8897bbb-868c-4163-b4c8-2e6baf356683', 'Lanche', 'e1defa99-5e85-4b34-906e-145b1f42bd57'),
       ('5e35868f-6b62-4037-ace5-aed309717357', 'Acompanhamento', 'e1defa99-5e85-4b34-906e-145b1f42bd57'),
       ('7982b8f9-e0f7-48a0-b3bb-53cdc03e52bd', 'Bebida', 'e1defa99-5e85-4b34-906e-145b1f42bd57'),
       ('1b4c406d-5bfc-41a3-9259-250cc95fdbb3', 'Sobremesa', 'e1defa99-5e85-4b34-906e-145b1f42bd57')
ON CONFLICT ("id") DO NOTHING;

INSERT INTO products (id, name, price, id_category, id_store)
VALUES ('c1ac8ade-16b6-4ed6-95eb-5ada38ceeab5', 'X-Bacon', 25.00, 'd8897bbb-868c-4163-b4c8-2e6baf356683',
        'e1defa99-5e85-4b34-906e-145b1f42bd57'),
       ('36d205da-2c2c-4079-a721-98f7524ccfe7', 'X-Salada', 21.00, 'd8897bbb-868c-4163-b4c8-2e6baf356683',
        'e1defa99-5e85-4b34-906e-145b1f42bd57'),
       ('53c8910e-bbca-4b3a-af66-e35398478d6e', 'X-Tudo', 30.00, 'd8897bbb-868c-4163-b4c8-2e6baf356683',
        'e1defa99-5e85-4b34-906e-145b1f42bd57'),

       ('6a302c83-33ff-49f9-ad6d-7adc7ff9b0f0', 'Batata Frita', 10.00, '5e35868f-6b62-4037-ace5-aed309717357',
        'e1defa99-5e85-4b34-906e-145b1f42bd57'),
       ('47e5a1bb-864a-45f6-91d8-dc2607ef53f5', 'Onion Rings', 15.00, '5e35868f-6b62-4037-ace5-aed309717357',
        'e1defa99-5e85-4b34-906e-145b1f42bd57'),
       ('22458e64-cef0-49c0-84d7-3554b48c89bc', 'Nuggets', 20.00, '5e35868f-6b62-4037-ace5-aed309717357',
        'e1defa99-5e85-4b34-906e-145b1f42bd57'),

       ('f0c541ab-0545-4241-b409-bba70cab6d33', 'Coca-Cola', 5.00, '7982b8f9-e0f7-48a0-b3bb-53cdc03e52bd',
        'e1defa99-5e85-4b34-906e-145b1f42bd57'),
       ('b51da555-7434-4235-a085-fd5e6e11633d', 'Guarana', 5.00, '7982b8f9-e0f7-48a0-b3bb-53cdc03e52bd',
        'e1defa99-5e85-4b34-906e-145b1f42bd57'),
       ('fdb798fb-095e-42cc-b44c-f78b978e8673', 'Fanta', 5.00, '7982b8f9-e0f7-48a0-b3bb-53cdc03e52bd',
        'e1defa99-5e85-4b34-906e-145b1f42bd57'),

       ('3db214e7-216f-4ddd-8009-4edb23500e22', 'Sorvete', 10.00, '1b4c406d-5bfc-41a3-9259-250cc95fdbb3',
        'e1defa99-5e85-4b34-906e-145b1f42bd57'),
       ('709d62d1-4dcb-4a88-a66c-bc47eaecd22e', 'Pudim', 10.00, '1b4c406d-5bfc-41a3-9259-250cc95fdbb3',
        'e1defa99-5e85-4b34-906e-145b1f42bd57'),
       ('6d8344b3-53a8-43be-ba2f-b8163b7acd2a', 'Mousse', 10.00, '1b4c406d-5bfc-41a3-9259-250cc95fdbb3',
        'e1defa99-5e85-4b34-906e-145b1f42bd57')
ON CONFLICT ("id") DO NOTHING;
