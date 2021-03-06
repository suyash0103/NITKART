from PIL import Image, ImageDraw
from django.http import JsonResponse, HttpResponse
from rest_framework import status
from rest_framework.response import Response
from rest_framework.views import APIView
from django.contrib.auth.hashers import make_password


from .models import User, Products, Images
from .serializers import UserSerializer, ProductSerializer, ImageSerializer


# Create your views here.

# for user in User.objects.all():
#     Token.objects.get_or_create(user=user)

class UserView(APIView):

    # def get(self, request):
    #     # items = Images.objects.all()
    #     # serializer = ImageSerializer(items, many=True)
    #     # return Response(serializer.data)
    #     products = Products.objects.all()
    #     serializer = ProductSerializer(products, many=True)
    #     return Response(serializer.data)

    def delete(self, request):
        Images.objects.filter(id=1).delete()
        Images.objects.filter(id=2).delete()
        Images.objects.filter(id=3).delete()
        return Response({'suc': 'succ'})


class Register(APIView):

    def post(self, request):
        print ("HEYYLO")
        username = request.data['username']
        password = request.data['password']
        email_id = request.data['email_id']
        phone_number = request.data['phone_number']
        # password = make_password(password)
        # print ("this is the password" + password)

        if User.objects.filter(username=username).exists():
            return Response({'Error': 'Username already exists'})
        elif User.objects.filter(email_id=email_id).exists():
            user = User.objects.get(email_id=email_id)
            if user.username:
                return Response({'Error': 'User already exists'})
            else:
                user.username = username
                user.password = make_password(password=password,
                                              salt=None,
                                              hasher='default')
                user.phone_number = phone_number
                user.save()
                serializer = UserSerializer(data=request.data)
                return Response(serializer.data, status=status.HTTP_201_CREATED)
        elif User.objects.filter(phone_number=phone_number).exists():
            return Response({'Error': 'Phone number already exists'})

        user = User()
        user.username = username
        user.password = make_password(password=password,
                                      salt=None,
                                      hasher='default')
        user.email_id = email_id
        user.phone_number = phone_number
        user.save()
        serializer = UserSerializer(data=request.data)
        if serializer.is_valid():
            # serializer.save()
            # print(serializer)
            # if serializer.save():
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.data, status=status.HTTP_400_BAD_REQUEST)


class Login(APIView):

    def post(self, request):
        username = request.POST.get('username')
        password = request.POST.get('password')
        print(username, password)

        try:
            user = User.objects.get(username=username)
            if user and user.password == password:
                print("USER MIL GAYA!")
                return Response({'Success': 'Logged In as ' + user.email_id}, status.HTTP_200_OK)
            elif user and user.password != password:
                return Response({'Error': 'Username and Password dont match'})
        except User.DoesNotExist:
            return Response({'Error': 'No such user exists, Register first'})
        return JsonResponse({'Error': 'Account Disabled'})


class Email(APIView):

    def post(self, request):
        email_id = request.POST.get('email_id')

        try:
            user = User.objects.get(email_id=email_id)
            if user:
                return Response({'Check Successfull': 'Email Id exists' + user.email_id}, status.HTTP_200_OK)
        except User.DoesNotExist:
            user = User(email_id=email_id)
            user.save()
            return Response({'Success': 'User Created' + user.email_id}, status.HTTP_201_CREATED)


# class Profile(APIView):
#
#     def post(self, request):
#         email_id = request.data['email_id']
#
#         try:
#             strF = ""
#             user = User.objects.get(email_id=email_id)
#             for product in Products.objects.filter(seller=user):
#                 strF = strF + " name : " + product.seller_name + " email : " + product.seller_email + " time period : " + str(
#                     product.time_period)
#             if strF == "":
#                 return Response({'Error': 'No Ads Posted'})
#             return Response({'Success': strF})
#         except User.DoesNotExist:
#             return Response({'Not Found': 'Not Found'})


# class Imageget(APIView):
#
#     def get(self, request):
#         size = (100, 50)  # size of the image to create
#         im = Image.new('RGB', size)  # create the image
#         draw = ImageDraw.Draw(im)  # create a drawing object that is
#         # used to draw on the new image
#         red = (255, 0, 0)  # color of our text
#         text_pos = (10, 10)  # top-left position of our text
#         text = "Hello World!"  # text to draw
#         # Now, we'll do the drawing:
#         draw.text(text_pos, text, fill=red)
#
#         del draw  # I'm done drawing so I don't need this anymore
#
#         # We need an HttpResponse object with the correct mimetype
#         response = HttpResponse(content_type="image/jpeg")
#         # now, we tell the image to save as a PNG to the
#         # provided file-like object
#         im.save(response, 'JPEG')
#
#         return response  # and we're done!
#
#     def post(self, request):
#         # imagee = Images()
#         # imagee.image = request.POST.get('image-prod')
#         # imagee.save()
#         # if imagee.save():
#         #     return Response({'success' : 'saved'})
#         # return Response({'error' : 'not saved'})
#
#         serializer = ImageSerializer(data=request.data)
#         if serializer.is_valid():
#             serializer.save()
#             print(serializer)
#             if serializer.is_valid():
#                 return Response(serializer.data, status=status.HTTP_201_CREATED)
#         return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class PostAd(APIView):

    def post(self, request):
        email_id = request.POST.get('seller_email')
        user = User.objects.get(email_id=email_id)
        if user:
            serializer = ProductSerializer(data=request.data)
            if serializer.is_valid():
                serializer.save()
                print(serializer)
                return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response({'Error': 'User with email id ' + email_id + ' not found'}, status.HTTP_404_NOT_FOUND)


class GetProducts(APIView):

    def get(self, request):
        products = Products.objects.all()
        serializer = ProductSerializer(products, many=True)
        return Response(serializer.data)

    def post(self, request):
        seller_email = request.POST.get('seller_email')
        products = Products.objects.filter(seller_email = seller_email)
        print (products)
        if products:
        #     urlList = []
        #     for product in products:
        #         urlList.append(str(product.image))
        #     return Response(str(urlList))
            serializer = ProductSerializer(products, many=True)
            return Response(serializer.data)
        return Response({'Error' : 'No Ads Posted'})

    def patch(self, request):
        products = Products.objects.all()
        serializer = ProductSerializer(products, partial=True)
        arr = []
        arr.append(serializer.data)
        print(arr)
        return JsonResponse(arr, safe=False)

class Delete(APIView):

    def delete(self, request):
        id = request.POST.get('id')
        Products.objects.filter(id = id).delete()
        return Response({'Success' : 'Deleted'})

class Test(APIView):

    def post(self, request):
        id = request.POST.get('id')
        product = Products.objects.get(id = id)
        product.image = request.POST.get('image')
        product.product_name = request.POST.get('product_name')
        product.seller_name = request.POST.get('seller_name')
        product.seller_phone = request.POST.get('seller_phone')
        product.seller_email = request.POST.get('seller_email')
        product.seller_block = request.POST.get('seller_block')
        product.seller_room = request.POST.get('seller_room')
        product.time_period = request.POST.get('time_period')
        product.product_price = request.POST.get('product_price')
        if product.save():
            return Response({'Success' : 'Updated'})
        return Response({'Error' : 'Not Updated'})
