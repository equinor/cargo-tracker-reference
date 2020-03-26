CREATE TABLE [CTREF].[file_upload](
	id nvarchar(36) primary key,
	name nvarchar(255) NULL,
	content varbinary(MAX) NULL,
	mimetype nvarchar(255) NULL,
	processing_error_msg nvarchar(2000) NULL,
	version int NOT NULL,
	updated_date_time [datetime] NOT NULL,
	updated_by nvarchar(255) NOT NULL
)
GO