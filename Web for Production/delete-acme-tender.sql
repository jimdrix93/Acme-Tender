start transaction;

drop user 'acme-user'@'%';
drop user 'acme-manager'@'%';

drop database `acme-tender`;

commit;
