PGDMP      2            
    |            Students    16.3    16.3 	    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16652    Students    DATABASE     ~   CREATE DATABASE "Students" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Russian_Russia.1251';
    DROP DATABASE "Students";
                postgres    false            �            1259    16654    student    TABLE       CREATE TABLE public.student (
    id bigint NOT NULL,
    surname character varying NOT NULL,
    name character varying NOT NULL,
    patronymic character varying,
    phone character varying,
    telegram character varying,
    email character varying,
    git character varying
);
    DROP TABLE public.student;
       public         heap    postgres    false            �            1259    16653    student_id_seq    SEQUENCE     �   ALTER TABLE public.student ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.student_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    216            �          0    16654    student 
   TABLE DATA           ]   COPY public.student (id, surname, name, patronymic, phone, telegram, email, git) FROM stdin;
    public          postgres    false    216   8	       �           0    0    student_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.student_id_seq', 19, true);
          public          postgres    false    215                       2606    16660    student student_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.student
    ADD CONSTRAINT student_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.student DROP CONSTRAINT student_pkey;
       public            postgres    false    216            �   	  x����j�@�ϣW)k�����N	��	A4��� '&��5���im)%��{�mQŎ��oԙ�[I��`#ͮ��7?��_���j�.p�N �3,�ox���±!��6Eׂ�g��ny��Z�	�F�;�t��aΰ��-�_���U�]am�:�0;]��f�Y�D^p�GG���<���a�S��7a2r.����xא�\!�ٵD�o�?b��
Ӱ�/�c���j�i��O�Pz���Ң����֋�4N'�%���&�>�\�՛r�R�Y|�KI{3���w��+7���8��xC�ģ���} �dÎ����.��s��f�%/\�>���$gٖ�*���i2�gUY]�sj��~�^�8&��%3�v_�1�C*�#����n�5V�Ϛ��<'�j��r����|�	e���(ueN�'�+\P����b��;6[k�#9�[Q艬6���iD�O׶����Jߒ�� ��S���	w�	w��o��ae�     