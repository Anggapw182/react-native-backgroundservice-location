-- public.mlocationhistory definition

-- DROP TABLE public.mlocationhistory;

CREATE TABLE public.mlocationhistory (
	pk_location_id int4 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	locationdatetime timestamp NULL,
	lat float4 NULL,
	lon float4 NULL,
	accuracy float4 NULL,
	userid varchar(50) null,
	uniqueid varchar(50) null,
	createddate timestamp NULL,
	createdby varchar(50) null,
	CONSTRAINT mlocationhistory_pk PRIMARY KEY (pk_location_id)
);