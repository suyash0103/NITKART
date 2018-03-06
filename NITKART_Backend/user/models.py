from django.db import models


class Users(models.Model):
    username = models.CharField(max_length=100, unique=True, null=True, blank=True)
    password = models.CharField(max_length=250, null=True, blank=True)
    email_id = models.CharField(max_length=50, unique=True)
    phone_number = models.IntegerField(unique=True, null=True, blank=True)

    def __str__(self):
        return self.email_id


class Products(models.Model):
    # seller = models.ForeignKey(Users, on_delete=models.CASCADE)
    image = models.ImageField()
    seller_name = models.CharField(max_length=100)
    seller_phone = models.CharField(max_length=20)
    seller_email = models.CharField(max_length=50)
    seller_block = models.IntegerField()
    seller_room = models.IntegerField()
    time_period = models.IntegerField()
    # price = models.IntegerField()


class Images(models.Model):
    image = models.FileField()
