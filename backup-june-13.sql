--
-- PostgreSQL database dump
--

-- Dumped from database version 11.5
-- Dumped by pg_dump version 12.3

-- Started on 2020-06-13 17:46:27

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 214 (class 1255 OID 16595)
-- Name: decrement_followers_count(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.decrement_followers_count() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	update public.user set 
	followers_count = followers_count - 1
	where user_id = old.followed_user_id;
	return old;
END;
$$;


ALTER FUNCTION public.decrement_followers_count() OWNER TO postgres;

--
-- TOC entry 215 (class 1255 OID 16591)
-- Name: decrement_likes_count(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.decrement_likes_count() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
begin
	update public.content set 
	likes_count = likes_count - 1
	where content_id = old.content_id;
	return old;
end;
$$;


ALTER FUNCTION public.decrement_likes_count() OWNER TO postgres;

--
-- TOC entry 213 (class 1255 OID 16594)
-- Name: increment_followers_count(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.increment_followers_count() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	update public.user set 
	followers_count = followers_count + 1
	where user_id = NEW.followed_user_id;
	return new;
END;
$$;


ALTER FUNCTION public.increment_followers_count() OWNER TO postgres;

--
-- TOC entry 212 (class 1255 OID 16590)
-- Name: increment_likes_count(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.increment_likes_count() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
begin
	update public.content set 
	likes_count = likes_count + 1
	where content_id = NEW.content_id;
	return new;
end;
$$;


ALTER FUNCTION public.increment_likes_count() OWNER TO postgres;

SET default_tablespace = '';

--
-- TOC entry 204 (class 1259 OID 16476)
-- Name: content; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.content (
    caption character varying(100),
    media_link character varying(100) NOT NULL,
    date_upload timestamp without time zone NOT NULL,
    content_id integer NOT NULL,
    user_id integer NOT NULL,
    title character varying,
    thumbnail_link character varying(150),
    likes_count integer DEFAULT 0 NOT NULL,
    tags character varying
);


ALTER TABLE public.content OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 16474)
-- Name: content_content_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.content_content_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.content_content_id_seq OWNER TO postgres;

--
-- TOC entry 3919 (class 0 OID 0)
-- Dependencies: 203
-- Name: content_content_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.content_content_id_seq OWNED BY public.content.content_id;


--
-- TOC entry 197 (class 1259 OID 16421)
-- Name: user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."user" (
    fname character varying(30) NOT NULL,
    avatar character varying(100),
    dob date NOT NULL,
    gender character(1) NOT NULL,
    email character varying(40) NOT NULL,
    password character varying(20) NOT NULL,
    date_created timestamp without time zone NOT NULL,
    user_id integer NOT NULL,
    lname character varying(30) NOT NULL,
    about character varying(200),
    type integer DEFAULT 1 NOT NULL,
    username character varying(20) DEFAULT 'user_name'::character varying NOT NULL,
    followers_count integer DEFAULT 0 NOT NULL
);


ALTER TABLE public."user" OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 16586)
-- Name: content_user_combined; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.content_user_combined AS
 SELECT co.caption,
    co.media_link,
    co.date_upload,
    co.content_id,
    co.user_id,
    co.title,
    co.thumbnail_link,
    co.likes_count,
    us.username,
    us.avatar,
    us.followers_count
   FROM public.content co,
    public."user" us
  WHERE (co.user_id = us.user_id);


ALTER TABLE public.content_user_combined OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 16569)
-- Name: files_temp; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.files_temp (
    file_id integer NOT NULL,
    file_path character varying NOT NULL,
    date_upload date NOT NULL,
    file_thumbnails text
);


ALTER TABLE public.files_temp OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 16567)
-- Name: files_temp_file_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.files_temp_file_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.files_temp_file_id_seq OWNER TO postgres;

--
-- TOC entry 3920 (class 0 OID 0)
-- Dependencies: 208
-- Name: files_temp_file_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.files_temp_file_id_seq OWNED BY public.files_temp.file_id;


--
-- TOC entry 206 (class 1259 OID 16511)
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 16488)
-- Name: likes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.likes (
    user_id integer NOT NULL,
    content_id integer NOT NULL
);


ALTER TABLE public.likes OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 16429)
-- Name: page; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.page (
    name character varying(30) NOT NULL,
    avatar character varying(100),
    about character varying(150) NOT NULL,
    date_created date NOT NULL,
    page_id integer NOT NULL,
    type character varying(20) NOT NULL,
    members text,
    user_id integer NOT NULL
);


ALTER TABLE public.page OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 16427)
-- Name: page_page_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.page_page_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.page_page_id_seq OWNER TO postgres;

--
-- TOC entry 3921 (class 0 OID 0)
-- Dependencies: 198
-- Name: page_page_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.page_page_id_seq OWNED BY public.page.page_id;


--
-- TOC entry 201 (class 1259 OID 16445)
-- Name: playlist; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.playlist (
    pname character varying(30) NOT NULL,
    playlist_id integer NOT NULL,
    date_created date NOT NULL,
    contents text,
    user_id integer NOT NULL
);


ALTER TABLE public.playlist OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 16443)
-- Name: playlist_playlist_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.playlist_playlist_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.playlist_playlist_id_seq OWNER TO postgres;

--
-- TOC entry 3922 (class 0 OID 0)
-- Dependencies: 200
-- Name: playlist_playlist_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.playlist_playlist_id_seq OWNED BY public.playlist.playlist_id;


--
-- TOC entry 202 (class 1259 OID 16459)
-- Name: ufollowsp; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ufollowsp (
    user_id integer NOT NULL,
    page_id integer NOT NULL
);


ALTER TABLE public.ufollowsp OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 16549)
-- Name: ufollowsu; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ufollowsu (
    follower_user_id integer NOT NULL,
    followed_user_id integer NOT NULL
);


ALTER TABLE public.ufollowsu OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 16600)
-- Name: user_listdto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_listdto (
    user_id integer NOT NULL,
    avatar character varying(255),
    fname character varying(255),
    lname character varying(255),
    username character varying(255)
);


ALTER TABLE public.user_listdto OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 16419)
-- Name: user_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.user_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_user_id_seq OWNER TO postgres;

--
-- TOC entry 3923 (class 0 OID 0)
-- Dependencies: 196
-- Name: user_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.user_user_id_seq OWNED BY public."user".user_id;


--
-- TOC entry 3741 (class 2604 OID 16479)
-- Name: content content_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.content ALTER COLUMN content_id SET DEFAULT nextval('public.content_content_id_seq'::regclass);


--
-- TOC entry 3743 (class 2604 OID 16572)
-- Name: files_temp file_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.files_temp ALTER COLUMN file_id SET DEFAULT nextval('public.files_temp_file_id_seq'::regclass);


--
-- TOC entry 3739 (class 2604 OID 16432)
-- Name: page page_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.page ALTER COLUMN page_id SET DEFAULT nextval('public.page_page_id_seq'::regclass);


--
-- TOC entry 3740 (class 2604 OID 16448)
-- Name: playlist playlist_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.playlist ALTER COLUMN playlist_id SET DEFAULT nextval('public.playlist_playlist_id_seq'::regclass);


--
-- TOC entry 3735 (class 2604 OID 16424)
-- Name: user user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user" ALTER COLUMN user_id SET DEFAULT nextval('public.user_user_id_seq'::regclass);


--
-- TOC entry 3906 (class 0 OID 16476)
-- Dependencies: 204
-- Data for Name: content; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.content (caption, media_link, date_upload, content_id, user_id, title, thumbnail_link, likes_count, tags) VALUES ('A Beautifull and mesmerising song by Rahat Fateh Ali Khan. #pakistani #romantic', 'uploads/1586036180633_afreen-coke-studio.mp4', '2020-04-04 00:00:00', 49, 2, 'Coke Studio 9 - Afreen Afreen', NULL, 2, 'hindi');
INSERT INTO public.content (caption, media_link, date_upload, content_id, user_id, title, thumbnail_link, likes_count, tags) VALUES ('A rock hit by 5 seconds of summer', 'uploads/1590175011227_5sos-she-looks-so-perfect.mp4', '2020-05-23 00:00:00', 122, 54, 'She Looks so perfect', 'uploads/thumbnails/1590175011227_5sos-she-looks-so-perfect.mp4_327.png', 2, NULL);
INSERT INTO public.content (caption, media_link, date_upload, content_id, user_id, title, thumbnail_link, likes_count, tags) VALUES ('The very first content on the website', 'uploads/Skillet_Feel_Invincible.mp4', '2020-02-25 00:00:00', 1, 1, NULL, NULL, 0, NULL);
INSERT INTO public.content (caption, media_link, date_upload, content_id, user_id, title, thumbnail_link, likes_count, tags) VALUES ('The Fat Rat - Unity', 'uploads/TheFatRat-Unity.mp4', '2020-02-26 00:00:00', 8, 2, NULL, NULL, 0, NULL);
INSERT INTO public.content (caption, media_link, date_upload, content_id, user_id, title, thumbnail_link, likes_count, tags) VALUES ('The very first content on the website', 'uploads/Skillet_Feel_Invincible.mp4', '2020-02-25 00:00:00', 9, 2, NULL, NULL, 0, NULL);
INSERT INTO public.content (caption, media_link, date_upload, content_id, user_id, title, thumbnail_link, likes_count, tags) VALUES ('The Fat Rat - Unity', 'uploads/TheFatRat-Unity.mp4', '2020-02-26 00:00:00', 4, 1, NULL, NULL, 1, NULL);
INSERT INTO public.content (caption, media_link, date_upload, content_id, user_id, title, thumbnail_link, likes_count, tags) VALUES ('The very first content on the website', 'uploads/Skillet_Feel_Invincible.mp4', '2020-02-25 00:00:00', 5, 2, NULL, NULL, 1, NULL);
INSERT INTO public.content (caption, media_link, date_upload, content_id, user_id, title, thumbnail_link, likes_count, tags) VALUES ('The all new EDM Hit by FatRat!', 'uploads/TheFatRat_Windfall.mp4', '2020-02-25 00:00:00', 6, 2, NULL, NULL, 1, NULL);
INSERT INTO public.content (caption, media_link, date_upload, content_id, user_id, title, thumbnail_link, likes_count, tags) VALUES ('A refresher during COVID! Enjoy 😉 ', 'uploads/1590314436970_trim.3869E51C-D517-4A29-9CE7-B2D98F2C4381.MOV', '2020-05-24 00:00:00', 137, 1, 'Fun during COVID', 'uploads/thumbnails/1590314436970_trim.3869E51C-D517-4A29-9CE7-B2D98F2C4381.MOV_437.png', 1, 'Fun,COVID19 ,Inspiration,Hope');
INSERT INTO public.content (caption, media_link, date_upload, content_id, user_id, title, thumbnail_link, likes_count, tags) VALUES ('The all new EDM Hit by FatRat!', 'uploads/TheFatRat_Windfall.mp4', '2020-02-27 00:00:00', 2, 1, NULL, NULL, 1, NULL);
INSERT INTO public.content (caption, media_link, date_upload, content_id, user_id, title, thumbnail_link, likes_count, tags) VALUES ('Teaching सा रे गा मा to My very first and cutest student  pranav', 'uploads/1590306346190_video_20170222_194539.mp4', '2020-05-24 00:00:00', 134, 55, 'Teaching सा रे गा मा to My very first and cutest student ', 'uploads/thumbnails/1590306346190_video_20170222_194539.mp4_57.png', 1, 'Saregama,Teaching,Alankar,अलंकार ');
INSERT INTO public.content (caption, media_link, date_upload, content_id, user_id, title, thumbnail_link, likes_count, tags) VALUES ('dedicated to Saaket', 'uploads/1591555351305_5sos-she-looks-so-perfect.mp4', '2020-06-07 00:00:00', 143, 1, 'She looks so perfect', 'uploads/thumbnails/1591555351305_5sos-she-looks-so-perfect.mp4_654.png', 2, 'english,rock,5sos');
INSERT INTO public.content (caption, media_link, date_upload, content_id, user_id, title, thumbnail_link, likes_count, tags) VALUES ('Fat Rat - Unity', 'uploads/1591892697625_TheFatRat-Unity.mp4', '2020-06-11 00:00:00', 178, 1, 'Unity', 'uploads/thumbnails/1591892697625_TheFatRat-Unity.mp4_389.png', 1, '');
INSERT INTO public.content (caption, media_link, date_upload, content_id, user_id, title, thumbnail_link, likes_count, tags) VALUES ('Khudi - Official', 'uploads/TheLocalTrain_Khudi.mp4', '2020-02-28 00:00:00', 7, 2, NULL, NULL, 1, NULL);
INSERT INTO public.content (caption, media_link, date_upload, content_id, user_id, title, thumbnail_link, likes_count, tags) VALUES ('intro with extra reverb. Do tell how it sounds.', 'uploads/1586166832756_VID-20200303-WA0002.mp4', '2020-04-06 00:00:00', 59, 1, 'Guns and Roses - Paradise city', NULL, 1, NULL);
INSERT INTO public.content (caption, media_link, date_upload, content_id, user_id, title, thumbnail_link, likes_count, tags) VALUES ('please like and follow!', 'uploads/1586084689820_VID-20200329-WA0007.mp4', '2020-04-05 00:00:00', 53, 1, 'First Song by me', NULL, 1, NULL);
INSERT INTO public.content (caption, media_link, date_upload, content_id, user_id, title, thumbnail_link, likes_count, tags) VALUES ('Amazing song by cranberries with a powerfull voice!', 'uploads/1590260185512_zombies.mp4', '2020-05-23 00:00:00', 131, 1, 'zombies', 'uploads/thumbnails/1590260185512_zombies.mp4_1840.png', 2, 'rock,punk,english,old');
INSERT INTO public.content (caption, media_link, date_upload, content_id, user_id, title, thumbnail_link, likes_count, tags) VALUES ('Rock song', 'uploads/1591895116592_Brodha V - Aathma Raama [Music Video].mkv', '2020-06-11 00:00:00', 182, 174, 'AAroha', 'uploads/thumbnails/1591895116592_Brodha V - Aathma Raama [Music Video].mkv_1444.png', 1, 'rock,Indian');
INSERT INTO public.content (caption, media_link, date_upload, content_id, user_id, title, thumbnail_link, likes_count, tags) VALUES ('Khudi - Official', 'uploads/TheLocalTrain_Khudi.mp4', '2020-02-28 00:00:00', 3, 1, NULL, NULL, 1, NULL);
INSERT INTO public.content (caption, media_link, date_upload, content_id, user_id, title, thumbnail_link, likes_count, tags) VALUES ('', 'uploads/1586074932647_passenger_let_her_go.mkv', '2020-04-05 00:00:00', 51, 1, 'Passenger -Let Her Go', NULL, 2, 'pop,romantic,english');
INSERT INTO public.content (caption, media_link, date_upload, content_id, user_id, title, thumbnail_link, likes_count, tags) VALUES ('An Amazing and motivating song by Club yoko. Must Listen! #rock', 'uploads/1586035356116_club-yoko-i-am.mkv', '2020-04-04 00:00:00', 47, 1, 'Club yoko - I Am', NULL, 0, 'rock,english');
INSERT INTO public.content (caption, media_link, date_upload, content_id, user_id, title, thumbnail_link, likes_count, tags) VALUES ('An Amazing motivational song by Club Yoko!', 'uploads/1586014305488_Club Yoko - I AM [Official Lyric Video] - YouTube.mkv', '2020-04-04 00:00:00', 43, 1, 'Club Yoko - I Am', NULL, 1, NULL);
INSERT INTO public.content (caption, media_link, date_upload, content_id, user_id, title, thumbnail_link, likes_count, tags) VALUES ('Classic number by Red hot chilli peppers', 'uploads/1590176701308_californication.mp4', '2020-05-22 00:00:00', 124, 54, 'Californication', 'uploads/thumbnails/1590176701308_californication.mp4_601.png', 0, NULL);
INSERT INTO public.content (caption, media_link, date_upload, content_id, user_id, title, thumbnail_link, likes_count, tags) VALUES ('Beautifulsong by Ali Zafar - coke studio season 8', 'uploads/1590171942133_Ae-dil-kisi-ki-yaad-me.mkv', '2020-05-22 00:00:00', 119, 54, 'Ae dil kisi ki yaad me...', 'uploads/thumbnails/1590171942133_Ae-dil-kisi-ki-yaad-me.mkv_605', 3, '');
INSERT INTO public.content (caption, media_link, date_upload, content_id, user_id, title, thumbnail_link, likes_count, tags) VALUES ('Let''s see how an old song sounds on distortion. ', 'uploads/1586149615323_VID_20180620_165939.mp4', '2020-04-06 00:00:00', 57, 55, 'Jara haule haule chalo more sajana ', NULL, 2, NULL);


--
-- TOC entry 3911 (class 0 OID 16569)
-- Dependencies: 209
-- Data for Name: files_temp; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.files_temp (file_id, file_path, date_upload, file_thumbnails) VALUES (44, 'uploads/1586027398796_club-yoko-i-am.mkv', '2020-04-05', NULL);
INSERT INTO public.files_temp (file_id, file_path, date_upload, file_thumbnails) VALUES (45, 'uploads/1586028350897_club-yoko-i-am.mkv', '2020-04-05', NULL);
INSERT INTO public.files_temp (file_id, file_path, date_upload, file_thumbnails) VALUES (115, 'uploads/1590169793246_5sos-she-looks-so-perfect.mp4', '2020-05-22', 'uploads/thumbnails/1590169793246_5sos-she-looks-so-perfect.mp4_327,uploads/thumbnails/1590169793246_5sos-she-looks-so-perfect.mp4_654,uploads/thumbnails/1590169793246_5sos-she-looks-so-perfect.mp4_981,uploads/thumbnails/1590169793246_5sos-she-looks-so-perfect.mp4_1308,');
INSERT INTO public.files_temp (file_id, file_path, date_upload, file_thumbnails) VALUES (120, 'uploads/1590174705763_5sos-she-looks-so-perfect.mp4', '2020-05-23', 'uploads/thumbnails/1590174705763_5sos-she-looks-so-perfect.mp4,uploads/thumbnails/1590174705763_5sos-she-looks-so-perfect.mp4,uploads/thumbnails/1590174705763_5sos-she-looks-so-perfect.mp4,uploads/thumbnails/1590174705763_5sos-she-looks-so-perfect.mp4,');
INSERT INTO public.files_temp (file_id, file_path, date_upload, file_thumbnails) VALUES (125, 'uploads/1590258528051_zombies.mp4', '2020-05-23', 'uploads/thumbnails/1590258528051_zombies.mp4_460.png,uploads/thumbnails/1590258528051_zombies.mp4_920.png,uploads/thumbnails/1590258528051_zombies.mp4_1380.png,uploads/thumbnails/1590258528051_zombies.mp4_1840.png,');
INSERT INTO public.files_temp (file_id, file_path, date_upload, file_thumbnails) VALUES (126, 'uploads/1590258645176_zombies.mp4', '2020-05-23', 'uploads/thumbnails/1590258645176_zombies.mp4_460.png,uploads/thumbnails/1590258645176_zombies.mp4_920.png,uploads/thumbnails/1590258645176_zombies.mp4_1380.png,uploads/thumbnails/1590258645176_zombies.mp4_1840.png,');
INSERT INTO public.files_temp (file_id, file_path, date_upload, file_thumbnails) VALUES (127, 'uploads/1590258752786_zombies.mp4', '2020-05-23', 'uploads/thumbnails/1590258752786_zombies.mp4_460.png,uploads/thumbnails/1590258752786_zombies.mp4_920.png,uploads/thumbnails/1590258752786_zombies.mp4_1380.png,uploads/thumbnails/1590258752786_zombies.mp4_1840.png,');
INSERT INTO public.files_temp (file_id, file_path, date_upload, file_thumbnails) VALUES (128, 'uploads/1590258885328_zombies.mp4', '2020-05-23', 'uploads/thumbnails/1590258885328_zombies.mp4_460.png,uploads/thumbnails/1590258885328_zombies.mp4_920.png,uploads/thumbnails/1590258885328_zombies.mp4_1380.png,uploads/thumbnails/1590258885328_zombies.mp4_1840.png,');
INSERT INTO public.files_temp (file_id, file_path, date_upload, file_thumbnails) VALUES (129, 'uploads/1590259861773_zombies.mp4', '2020-05-23', 'uploads/thumbnails/1590259861773_zombies.mp4_460.png,uploads/thumbnails/1590259861773_zombies.mp4_920.png,uploads/thumbnails/1590259861773_zombies.mp4_1380.png,uploads/thumbnails/1590259861773_zombies.mp4_1840.png,');
INSERT INTO public.files_temp (file_id, file_path, date_upload, file_thumbnails) VALUES (132, 'uploads/1590302660898_video_20170222_194539.mp4', '2020-05-24', 'uploads/thumbnails/1590302660898_video_20170222_194539.mp4_19.png,uploads/thumbnails/1590302660898_video_20170222_194539.mp4_38.png,uploads/thumbnails/1590302660898_video_20170222_194539.mp4_57.png,uploads/thumbnails/1590302660898_video_20170222_194539.mp4_76.png,');
INSERT INTO public.files_temp (file_id, file_path, date_upload, file_thumbnails) VALUES (165, 'uploads/1591781969619_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4', '2020-06-10', 'uploads/thumbnails/1591781969619_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_444.png,uploads/thumbnails/1591781969619_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_888.png,uploads/thumbnails/1591781969619_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_1332.png,uploads/thumbnails/1591781969619_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_1776.png,');
INSERT INTO public.files_temp (file_id, file_path, date_upload, file_thumbnails) VALUES (166, 'uploads/1591783320361_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4', '2020-06-10', 'uploads/thumbnails/1591783320361_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_444.png,uploads/thumbnails/1591783320361_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_888.png,uploads/thumbnails/1591783320361_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_1332.png,uploads/thumbnails/1591783320361_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_1776.png,');
INSERT INTO public.files_temp (file_id, file_path, date_upload, file_thumbnails) VALUES (167, 'uploads/1591783385607_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4', '2020-06-10', 'uploads/thumbnails/1591783385607_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_444.png,uploads/thumbnails/1591783385607_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_888.png,uploads/thumbnails/1591783385607_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_1332.png,uploads/thumbnails/1591783385607_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_1776.png,');
INSERT INTO public.files_temp (file_id, file_path, date_upload, file_thumbnails) VALUES (168, 'uploads/1591784197575_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4', '2020-06-10', 'uploads/thumbnails/1591784197575_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_444.png,uploads/thumbnails/1591784197575_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_888.png,uploads/thumbnails/1591784197575_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_1332.png,uploads/thumbnails/1591784197575_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_1776.png,');
INSERT INTO public.files_temp (file_id, file_path, date_upload, file_thumbnails) VALUES (169, 'uploads/1591784301714_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4', '2020-06-10', 'uploads/thumbnails/1591784301714_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_444.png,uploads/thumbnails/1591784301714_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_888.png,uploads/thumbnails/1591784301714_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_1332.png,uploads/thumbnails/1591784301714_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_1776.png,');
INSERT INTO public.files_temp (file_id, file_path, date_upload, file_thumbnails) VALUES (170, 'uploads/1591784476793_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4', '2020-06-10', 'uploads/thumbnails/1591784476793_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_444.png,uploads/thumbnails/1591784476793_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_888.png,uploads/thumbnails/1591784476793_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_1332.png,uploads/thumbnails/1591784476793_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_1776.png,');
INSERT INTO public.files_temp (file_id, file_path, date_upload, file_thumbnails) VALUES (171, 'uploads/1591784938821_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4', '2020-06-10', 'uploads/thumbnails/1591784938821_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_444.png,uploads/thumbnails/1591784938821_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_888.png,uploads/thumbnails/1591784938821_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_1332.png,uploads/thumbnails/1591784938821_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_1776.png,');
INSERT INTO public.files_temp (file_id, file_path, date_upload, file_thumbnails) VALUES (172, 'uploads/1591785181988_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4', '2020-06-10', 'uploads/thumbnails/1591785181988_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_444.png,uploads/thumbnails/1591785181988_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_888.png,uploads/thumbnails/1591785181988_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_1332.png,uploads/thumbnails/1591785181988_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_1776.png,');
INSERT INTO public.files_temp (file_id, file_path, date_upload, file_thumbnails) VALUES (173, 'uploads/1591785365144_Brodha V - Aathma Raama [Music Video].mkv', '2020-06-10', 'uploads/thumbnails/1591785365144_Brodha V - Aathma Raama [Music Video].mkv_361.png,uploads/thumbnails/1591785365144_Brodha V - Aathma Raama [Music Video].mkv_722.png,uploads/thumbnails/1591785365144_Brodha V - Aathma Raama [Music Video].mkv_1083.png,uploads/thumbnails/1591785365144_Brodha V - Aathma Raama [Music Video].mkv_1444.png,');
INSERT INTO public.files_temp (file_id, file_path, date_upload, file_thumbnails) VALUES (176, 'uploads/1591893575632_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4', '2020-06-11', 'uploads/thumbnails/1591893575632_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_444.png,uploads/thumbnails/1591893575632_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_888.png,uploads/thumbnails/1591893575632_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_1332.png,uploads/thumbnails/1591893575632_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_1776.png,');
INSERT INTO public.files_temp (file_id, file_path, date_upload, file_thumbnails) VALUES (177, 'uploads/1591893880895_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4', '2020-06-11', 'uploads/thumbnails/1591893880895_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_444.png,uploads/thumbnails/1591893880895_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_888.png,uploads/thumbnails/1591893880895_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_1332.png,uploads/thumbnails/1591893880895_Aigiri Nandini [Rock Version] - Official Music Video - Nakshatra Productions.mp4_1776.png,');


--
-- TOC entry 3907 (class 0 OID 16488)
-- Dependencies: 205
-- Data for Name: likes; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.likes (user_id, content_id) VALUES (55, 59);
INSERT INTO public.likes (user_id, content_id) VALUES (55, 51);
INSERT INTO public.likes (user_id, content_id) VALUES (1, 43);
INSERT INTO public.likes (user_id, content_id) VALUES (2, 49);
INSERT INTO public.likes (user_id, content_id) VALUES (1, 2);
INSERT INTO public.likes (user_id, content_id) VALUES (55, 57);
INSERT INTO public.likes (user_id, content_id) VALUES (1, 51);
INSERT INTO public.likes (user_id, content_id) VALUES (1, 57);
INSERT INTO public.likes (user_id, content_id) VALUES (1, 122);
INSERT INTO public.likes (user_id, content_id) VALUES (1, 131);
INSERT INTO public.likes (user_id, content_id) VALUES (55, 131);
INSERT INTO public.likes (user_id, content_id) VALUES (55, 122);
INSERT INTO public.likes (user_id, content_id) VALUES (55, 119);
INSERT INTO public.likes (user_id, content_id) VALUES (1, 4);
INSERT INTO public.likes (user_id, content_id) VALUES (1, 5);
INSERT INTO public.likes (user_id, content_id) VALUES (1, 6);
INSERT INTO public.likes (user_id, content_id) VALUES (1, 137);
INSERT INTO public.likes (user_id, content_id) VALUES (55, 143);
INSERT INTO public.likes (user_id, content_id) VALUES (1, 7);
INSERT INTO public.likes (user_id, content_id) VALUES (1, 49);
INSERT INTO public.likes (user_id, content_id) VALUES (1, 53);
INSERT INTO public.likes (user_id, content_id) VALUES (1, 3);
INSERT INTO public.likes (user_id, content_id) VALUES (1, 143);
INSERT INTO public.likes (user_id, content_id) VALUES (1, 178);
INSERT INTO public.likes (user_id, content_id) VALUES (1, 182);
INSERT INTO public.likes (user_id, content_id) VALUES (1, 119);
INSERT INTO public.likes (user_id, content_id) VALUES (147, 119);
INSERT INTO public.likes (user_id, content_id) VALUES (1, 134);


--
-- TOC entry 3901 (class 0 OID 16429)
-- Dependencies: 199
-- Data for Name: page; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.page (name, avatar, about, date_created, page_id, type, members, user_id) VALUES ('Skrillex Music', NULL, 'Student, Guitarist, Great fan of John Petruci', '2020-02-25', 1, 'Individual', '{}', 1);
INSERT INTO public.page (name, avatar, about, date_created, page_id, type, members, user_id) VALUES ('Sa Re Ga Ma', NULL, 'High Quality Hindi Music for every age group!', '2020-02-24', 2, 'Group', '{}', 2);


--
-- TOC entry 3903 (class 0 OID 16445)
-- Dependencies: 201
-- Data for Name: playlist; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.playlist (pname, playlist_id, date_created, contents, user_id) VALUES ('Indie', 33, '2020-05-14', ',51,57,119', 1);
INSERT INTO public.playlist (pname, playlist_id, date_created, contents, user_id) VALUES ('English Songs', 2, '2020-02-26', ',43,51,3,178', 1);
INSERT INTO public.playlist (pname, playlist_id, date_created, contents, user_id) VALUES ('Funnnnnn', 5, '2020-02-28', '', 40);
INSERT INTO public.playlist (pname, playlist_id, date_created, contents, user_id) VALUES ('Breeze', 6, '2020-02-28', '', 40);
INSERT INTO public.playlist (pname, playlist_id, date_created, contents, user_id) VALUES ('Breeze', 7, '2020-02-28', '', 40);
INSERT INTO public.playlist (pname, playlist_id, date_created, contents, user_id) VALUES ('Calm songs', 8, '2020-02-28', '', 40);
INSERT INTO public.playlist (pname, playlist_id, date_created, contents, user_id) VALUES ('wonderful', 9, '2020-02-28', '', 40);
INSERT INTO public.playlist (pname, playlist_id, date_created, contents, user_id) VALUES ('new', 10, '2020-02-28', '', 40);
INSERT INTO public.playlist (pname, playlist_id, date_created, contents, user_id) VALUES ('Dummy', 11, '2020-02-28', '', 40);
INSERT INTO public.playlist (pname, playlist_id, date_created, contents, user_id) VALUES ('mega', 12, '2020-02-29', '', 40);
INSERT INTO public.playlist (pname, playlist_id, date_created, contents, user_id) VALUES ('mega', 13, '2020-02-29', '', 40);
INSERT INTO public.playlist (pname, playlist_id, date_created, contents, user_id) VALUES ('Iiitdmj ', 21, '2020-05-09', '', 55);
INSERT INTO public.playlist (pname, playlist_id, date_created, contents, user_id) VALUES ('Junk', 22, '2020-05-10', '', 55);
INSERT INTO public.playlist (pname, playlist_id, date_created, contents, user_id) VALUES ('Iiitdmj ', 20, '2020-05-09', ',59,51', 55);
INSERT INTO public.playlist (pname, playlist_id, date_created, contents, user_id) VALUES ('Motivational', 34, '2020-05-14', ',59,57', 1);
INSERT INTO public.playlist (pname, playlist_id, date_created, contents, user_id) VALUES ('JPop', 3, '2020-02-27', ',59,131', 1);
INSERT INTO public.playlist (pname, playlist_id, date_created, contents, user_id) VALUES ('Favourite ', 36, '2020-05-24', ',131,119', 55);
INSERT INTO public.playlist (pname, playlist_id, date_created, contents, user_id) VALUES ('Classics', 35, '2020-05-15', ',47,137', 1);
INSERT INTO public.playlist (pname, playlist_id, date_created, contents, user_id) VALUES ('newp', 17, '2020-04-22', ',3,131,137', 1);
INSERT INTO public.playlist (pname, playlist_id, date_created, contents, user_id) VALUES ('JPop', 4, '2020-02-28', ',57,47,137', 1);
INSERT INTO public.playlist (pname, playlist_id, date_created, contents, user_id) VALUES ('Rock', 32, '2020-05-14', ',59,122,131,137,134', 1);
INSERT INTO public.playlist (pname, playlist_id, date_created, contents, user_id) VALUES ('Tremollo Specials', 38, '2020-06-07', ',131,119,124', 147);
INSERT INTO public.playlist (pname, playlist_id, date_created, contents, user_id) VALUES ('Cool', 39, '2020-06-07', ',57,51,143,134', 147);
INSERT INTO public.playlist (pname, playlist_id, date_created, contents, user_id) VALUES ('Indie', 40, '2020-06-08', '', 147);
INSERT INTO public.playlist (pname, playlist_id, date_created, contents, user_id) VALUES ('Band Band', 41, '2020-06-08', '', 147);
INSERT INTO public.playlist (pname, playlist_id, date_created, contents, user_id) VALUES ('Evergreen Rocks', 42, '2020-06-08', '', 147);
INSERT INTO public.playlist (pname, playlist_id, date_created, contents, user_id) VALUES ('Romantics jukes', 43, '2020-06-08', '', 147);
INSERT INTO public.playlist (pname, playlist_id, date_created, contents, user_id) VALUES ('Tatti', 44, '2020-06-11', ',143', 174);
INSERT INTO public.playlist (pname, playlist_id, date_created, contents, user_id) VALUES ('Weak tatti', 45, '2020-06-11', '', 174);
INSERT INTO public.playlist (pname, playlist_id, date_created, contents, user_id) VALUES ('Zehereeli Tatti', 46, '2020-06-11', ',57', 174);


--
-- TOC entry 3904 (class 0 OID 16459)
-- Dependencies: 202
-- Data for Name: ufollowsp; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.ufollowsp (user_id, page_id) VALUES (2, 1);
INSERT INTO public.ufollowsp (user_id, page_id) VALUES (1, 2);


--
-- TOC entry 3909 (class 0 OID 16549)
-- Dependencies: 207
-- Data for Name: ufollowsu; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.ufollowsu (follower_user_id, followed_user_id) VALUES (147, 55);
INSERT INTO public.ufollowsu (follower_user_id, followed_user_id) VALUES (147, 1);
INSERT INTO public.ufollowsu (follower_user_id, followed_user_id) VALUES (174, 55);
INSERT INTO public.ufollowsu (follower_user_id, followed_user_id) VALUES (174, 1);
INSERT INTO public.ufollowsu (follower_user_id, followed_user_id) VALUES (174, 174);
INSERT INTO public.ufollowsu (follower_user_id, followed_user_id) VALUES (1, 174);
INSERT INTO public.ufollowsu (follower_user_id, followed_user_id) VALUES (1, 55);
INSERT INTO public.ufollowsu (follower_user_id, followed_user_id) VALUES (1, 1);


--
-- TOC entry 3899 (class 0 OID 16421)
-- Dependencies: 197
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public."user" (fname, avatar, dob, gender, email, password, date_created, user_id, lname, about, type, username, followers_count) VALUES ('Saaket', NULL, '1998-10-04', 'M', 'saaket123@gmail.com', 'sexysexysexy', '2020-02-08 15:15:15', 2, 'Chawali
', NULL, 1, 'user_name', 0);
INSERT INTO public."user" (fname, avatar, dob, gender, email, password, date_created, user_id, lname, about, type, username, followers_count) VALUES ('Shyam', '1582700165581_Capture.PNG', '1998-04-11', 'M', 'shyam@1234', '12345678', '2020-02-26 12:26:05.575', 3, 'Sundar', NULL, 1, 'user_name', 0);
INSERT INTO public."user" (fname, avatar, dob, gender, email, password, date_created, user_id, lname, about, type, username, followers_count) VALUES ('Rahul', 'User/Avatar/1582744455365_avatar.jpg', '1998-04-11', 'M', 'rahul@456', '87654321', '2020-02-26 19:14:15.353', 5, 'Dravid', NULL, 1, 'user_name', 0);
INSERT INTO public."user" (fname, avatar, dob, gender, email, password, date_created, user_id, lname, about, type, username, followers_count) VALUES ('Honey', '', '1998-10-15', 'M', 'honeys@gmail.com', '87654321', '2020-02-28 00:02:29.918', 9, 'Singh', NULL, 1, 'user_name', 0);
INSERT INTO public."user" (fname, avatar, dob, gender, email, password, date_created, user_id, lname, about, type, username, followers_count) VALUES ('Daniel', '', '1998-04-10', 'M', 'daniel@456', '87654321', '2020-02-27 19:26:08.636', 10, 'McCarthy', NULL, 1, 'user_name', 0);
INSERT INTO public."user" (fname, avatar, dob, gender, email, password, date_created, user_id, lname, about, type, username, followers_count) VALUES ('we', '', '2020-02-06', 'M', 'wew@work.com', 'wewewewe', '2020-02-28 08:18:56.009', 20, 'work', NULL, 1, 'user_name', 0);
INSERT INTO public."user" (fname, avatar, dob, gender, email, password, date_created, user_id, lname, about, type, username, followers_count) VALUES ('We', '', '2020-02-14', 'M', 'we@work.com', 'wework', '2020-02-28 08:22:59.725', 23, 'Work', NULL, 1, 'user_name', 0);
INSERT INTO public."user" (fname, avatar, dob, gender, email, password, date_created, user_id, lname, about, type, username, followers_count) VALUES ('Daniel', 'User/Avatar/1582741414631_Capture.PNG', '1998-04-11', 'M', 'daniel@4567', '87654321', '2020-02-26 23:53:34.616', 4, 'McCarthy', NULL, 1, 'user_name', 0);
INSERT INTO public."user" (fname, avatar, dob, gender, email, password, date_created, user_id, lname, about, type, username, followers_count) VALUES ('Gk', '', '2020-02-21', 'M', 'gk@mail.com', 'gkgkgk', '2020-02-28 12:16:14.776', 24, 'kg', NULL, 1, 'user_name', 0);
INSERT INTO public."user" (fname, avatar, dob, gender, email, password, date_created, user_id, lname, about, type, username, followers_count) VALUES ('Yao', '', '2020-02-07', 'M', 'yaoi@mail.com', 'yaooiyaoi', '2020-02-28 12:26:06.182', 25, 'ui', NULL, 1, 'user_name', 0);
INSERT INTO public."user" (fname, avatar, dob, gender, email, password, date_created, user_id, lname, about, type, username, followers_count) VALUES ('srk', '', '2020-02-04', 'M', 'srk@gmail.com', 'srksrk', '2020-02-28 12:58:09.538', 37, 'srk', NULL, 1, 'user_name', 0);
INSERT INTO public."user" (fname, avatar, dob, gender, email, password, date_created, user_id, lname, about, type, username, followers_count) VALUES ('abc', '', '2020-02-04', 'M', 'abc@gmail.com', 'abcabc', '2020-02-28 13:07:43.671', 38, 'corp', NULL, 1, 'user_name', 0);
INSERT INTO public."user" (fname, avatar, dob, gender, email, password, date_created, user_id, lname, about, type, username, followers_count) VALUES ('qweer', '', '2020-02-04', 'M', 'qwerty@mail.com', 'qwerty', '2020-02-28 14:14:54.133', 39, 'qwqeqe', NULL, 1, 'user_name', 0);
INSERT INTO public."user" (fname, avatar, dob, gender, email, password, date_created, user_id, lname, about, type, username, followers_count) VALUES ('Saaket ', '', '1998-04-10', 'M', 'saaketchawali777@gmail.com', 'saaket123', '2020-02-28 16:26:49.737', 40, 'Chawali', NULL, 1, 'user_name', 0);
INSERT INTO public."user" (fname, avatar, dob, gender, email, password, date_created, user_id, lname, about, type, username, followers_count) VALUES ('New ', '', '2020-02-10', 'M', 'new@mail.com', 'newnew', '2020-02-29 07:43:11.653', 41, 'user', NULL, 1, 'user_name', 0);
INSERT INTO public."user" (fname, avatar, dob, gender, email, password, date_created, user_id, lname, about, type, username, followers_count) VALUES ('Kratika', NULL, '2005-02-03', 'F', 'jainkratika.567@gmail.com', 'loyal to royals', '2020-04-08 14:01:11.214', 60, 'Jain', 'Upcoming superstar. Best pionist
', 0, 'Kratika', 0);
INSERT INTO public."user" (fname, avatar, dob, gender, email, password, date_created, user_id, lname, about, type, username, followers_count) VALUES ('test', NULL, '2020-05-01', 'M', 'test1001@mail.com', 'test1234', '2020-05-10 10:53:53.418', 113, '1001', 'nothing about me', 3, 'test1001', 0);
INSERT INTO public."user" (fname, avatar, dob, gender, email, password, date_created, user_id, lname, about, type, username, followers_count) VALUES ('Rahul', 'User/Avatar/1591635018735_Dream (2).jfif', '2020-06-10', 'M', 'rs@mail.com', 'rahulsharma', '2020-06-08 16:49:50.348', 148, 'Sharma', 'A good listener.', 2, 'Rahul123', 0);
INSERT INTO public."user" (fname, avatar, dob, gender, email, password, date_created, user_id, lname, about, type, username, followers_count) VALUES ('KKK', NULL, '2020-06-05', 'M', 'ii@mail.com', 'iiooiiooiio', '2020-06-08 19:01:56.018', 149, 'nnn', 'slkskdns', 2, 'ioi122`', 0);
INSERT INTO public."user" (fname, avatar, dob, gender, email, password, date_created, user_id, lname, about, type, username, followers_count) VALUES ('Test', NULL, '2010-04-02', 'M', 'test1@mail', 'test1234', '2020-04-05 21:28:50.139', 54, 'Man', 'I am a test user, whose sole purpose is to test the application', 1, 'test1', 2);
INSERT INTO public."user" (fname, avatar, dob, gender, email, password, date_created, user_id, lname, about, type, username, followers_count) VALUES ('ladki', NULL, '2020-05-04', 'F', 'ladki123@mail.com', '12345678', '2020-06-07 19:35:21.457', 145, 'singh', 'fasdfsa dfasd fads fsad fs', 4, 'ladki123', 0);
INSERT INTO public."user" (fname, avatar, dob, gender, email, password, date_created, user_id, lname, about, type, username, followers_count) VALUES ('Ruby', 'User/Avatar/1591889603989_avatar.jpg', '2020-06-10', 'F', 'ruby@gmail.com', 'rubyruby', '2020-06-11 15:29:53.876', 174, 'Singh', 'In search for love and good vibes......', 4, 'ruby123', 3);
INSERT INTO public."user" (fname, avatar, dob, gender, email, password, date_created, user_id, lname, about, type, username, followers_count) VALUES ('Saaket', 'User/Avatar/1591699214987_profilePic.svg', '1998-04-10', 'M', 'saaketchawali@rocketmail.com', 'tremollo', '2020-06-09 16:32:45.729', 147, 'Chawali', 'Thinker, explorer, and believes in value based innovation.', 0, 'kay777', 0);
INSERT INTO public."user" (fname, avatar, dob, gender, email, password, date_created, user_id, lname, about, type, username, followers_count) VALUES ('Ram Raj ', 'User/Avatar/1587150124533_66469956_2346967138756820_2266544041275424768_o.jpg', '1995-10-28', 'M', 'ramrajvishvakarma@gmail.com', 'Gibson$159#', '2020-05-24 07:55:21.91', 55, 'Vishvakarma', 'I''m a music lover exploring and creating music everywhere and everyday. Do you wanna have some sex? ', 0, 'creative.ram', 7);
INSERT INTO public."user" (fname, avatar, dob, gender, email, password, date_created, user_id, lname, about, type, username, followers_count) VALUES ('Somya', 'User/Avatar/1591718527033_develop.svg', '1998-02-11', 'M', 'jainsomya972@gmail.com', 'sexsexsex', '2020-06-07 18:35:23.363', 1, 'Jain', 'A passionate guitarist ', 1, 'jain_somya', 15);


--
-- TOC entry 3912 (class 0 OID 16600)
-- Dependencies: 211
-- Data for Name: user_listdto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3924 (class 0 OID 0)
-- Dependencies: 203
-- Name: content_content_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.content_content_id_seq', 1, false);


--
-- TOC entry 3925 (class 0 OID 0)
-- Dependencies: 208
-- Name: files_temp_file_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.files_temp_file_id_seq', 1, false);


--
-- TOC entry 3926 (class 0 OID 0)
-- Dependencies: 206
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hibernate_sequence', 182, true);


--
-- TOC entry 3927 (class 0 OID 0)
-- Dependencies: 198
-- Name: page_page_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.page_page_id_seq', 1, false);


--
-- TOC entry 3928 (class 0 OID 0)
-- Dependencies: 200
-- Name: playlist_playlist_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.playlist_playlist_id_seq', 46, true);


--
-- TOC entry 3929 (class 0 OID 0)
-- Dependencies: 196
-- Name: user_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_user_id_seq', 2, true);


--
-- TOC entry 3755 (class 2606 OID 16481)
-- Name: content content_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.content
    ADD CONSTRAINT content_pkey PRIMARY KEY (content_id);


--
-- TOC entry 3761 (class 2606 OID 16577)
-- Name: files_temp files_temp_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.files_temp
    ADD CONSTRAINT files_temp_pkey PRIMARY KEY (file_id);


--
-- TOC entry 3757 (class 2606 OID 16492)
-- Name: likes likes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.likes
    ADD CONSTRAINT likes_pkey PRIMARY KEY (user_id, content_id);


--
-- TOC entry 3749 (class 2606 OID 16437)
-- Name: page page_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.page
    ADD CONSTRAINT page_pkey PRIMARY KEY (page_id);


--
-- TOC entry 3751 (class 2606 OID 16453)
-- Name: playlist playlist_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.playlist
    ADD CONSTRAINT playlist_pkey PRIMARY KEY (playlist_id);


--
-- TOC entry 3753 (class 2606 OID 16463)
-- Name: ufollowsp ufollowsp_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ufollowsp
    ADD CONSTRAINT ufollowsp_pkey PRIMARY KEY (user_id, page_id);


--
-- TOC entry 3759 (class 2606 OID 16553)
-- Name: ufollowsu ufollowsu_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ufollowsu
    ADD CONSTRAINT ufollowsu_pkey PRIMARY KEY (follower_user_id, followed_user_id);


--
-- TOC entry 3745 (class 2606 OID 16547)
-- Name: user unique_email; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT unique_email UNIQUE (email);


--
-- TOC entry 3763 (class 2606 OID 16607)
-- Name: user_listdto user_listdto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_listdto
    ADD CONSTRAINT user_listdto_pkey PRIMARY KEY (user_id);


--
-- TOC entry 3747 (class 2606 OID 16426)
-- Name: user user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (user_id);


--
-- TOC entry 3772 (class 2620 OID 16599)
-- Name: likes dislike_content; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER dislike_content BEFORE DELETE ON public.likes FOR EACH ROW EXECUTE PROCEDURE public.decrement_likes_count();


--
-- TOC entry 3774 (class 2620 OID 16596)
-- Name: ufollowsu increment_followers_count; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER increment_followers_count AFTER INSERT ON public.ufollowsu FOR EACH ROW EXECUTE PROCEDURE public.increment_followers_count();


--
-- TOC entry 3773 (class 2620 OID 16592)
-- Name: likes like_content; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER like_content AFTER INSERT ON public.likes FOR EACH ROW EXECUTE PROCEDURE public.increment_likes_count();


--
-- TOC entry 3775 (class 2620 OID 16597)
-- Name: ufollowsu unfollow_user; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER unfollow_user AFTER DELETE ON public.ufollowsu FOR EACH ROW EXECUTE PROCEDURE public.increment_followers_count();


--
-- TOC entry 3769 (class 2606 OID 16498)
-- Name: likes likes_content_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.likes
    ADD CONSTRAINT likes_content_id_fkey FOREIGN KEY (content_id) REFERENCES public.content(content_id);


--
-- TOC entry 3768 (class 2606 OID 16493)
-- Name: likes likes_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.likes
    ADD CONSTRAINT likes_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(user_id);


--
-- TOC entry 3764 (class 2606 OID 16438)
-- Name: page page_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.page
    ADD CONSTRAINT page_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(user_id);


--
-- TOC entry 3765 (class 2606 OID 16454)
-- Name: playlist playlist_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.playlist
    ADD CONSTRAINT playlist_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(user_id);


--
-- TOC entry 3767 (class 2606 OID 16469)
-- Name: ufollowsp ufollowsp_page_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ufollowsp
    ADD CONSTRAINT ufollowsp_page_id_fkey FOREIGN KEY (page_id) REFERENCES public.page(page_id);


--
-- TOC entry 3766 (class 2606 OID 16464)
-- Name: ufollowsp ufollowsp_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ufollowsp
    ADD CONSTRAINT ufollowsp_user_id_fkey FOREIGN KEY (user_id) REFERENCES public."user"(user_id);


--
-- TOC entry 3771 (class 2606 OID 16559)
-- Name: ufollowsu ufollowsu_followed_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ufollowsu
    ADD CONSTRAINT ufollowsu_followed_id_fkey FOREIGN KEY (followed_user_id) REFERENCES public."user"(user_id);


--
-- TOC entry 3770 (class 2606 OID 16554)
-- Name: ufollowsu ufollowsu_follower_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ufollowsu
    ADD CONSTRAINT ufollowsu_follower_id_fkey FOREIGN KEY (follower_user_id) REFERENCES public."user"(user_id);


--
-- TOC entry 3918 (class 0 OID 0)
-- Dependencies: 3
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM rdsadmin;
REVOKE ALL ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2020-06-13 17:46:35

--
-- PostgreSQL database dump complete
--

