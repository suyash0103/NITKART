from django.db import models


class Users(models.Model):
    username = models.CharField(max_length=100, unique=True, null=True, blank=True)
    password = models.CharField(max_length=250, null=True, blank=True)
    email_id = models.CharField(max_length=50, unique=True)
    phone_number = models.IntegerField(unique=True, null=True, blank=True)

    def __str__(self):
        return self.email_id


class Products(models.Model):
    image = models.FileField(null=True, blank=True)
    product_name = models.CharField(max_length=1000)
    seller_name = models.CharField(max_length=100)
    seller_phone = models.CharField(max_length=20)
    seller_email = models.CharField(max_length=50)
    seller_block = models.CharField(max_length=20)
    seller_room = models.CharField(max_length=20)
    time_period = models.CharField(max_length=10)
    product_price = models.CharField(max_length=50)


class Images(models.Model):
    image = models.FileField()
