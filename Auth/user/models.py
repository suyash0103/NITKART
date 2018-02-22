from django.db import models

# Create your models here.

class Users(models.Model):
    name = models.CharField(max_length=100)
    phone_number = models.IntegerField()
    email_id = models.CharField(max_length=50)
    password = models.CharField(max_length=250)
    # token = models.CharField(max_length=250)

    def __str__(self):
        return self.name


class On_Sale(models.Model):
    user = models.ForeignKey(Users, on_delete=models.CASCADE)
    block = models.IntegerField()
    room = models.IntegerField()
    time_period = models.IntegerField()


class Products(models.Model):
    seller_name = models.CharField(max_length=100)
    seller_phone = models.CharField(max_length=20)
    seller_email = models.CharField(max_length=50)
    seller_block = models.IntegerField()
    seller_room = models.IntegerField()
    time_period = models.IntegerField()
