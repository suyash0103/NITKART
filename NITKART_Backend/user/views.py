from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from .models import Users, Products
from .serializers import UserSerializer, ProductSerializer
from django.http import JsonResponse, HttpResponse
from rest_framework.response import Response
from PIL import Image, ImageDraw

# Create your views here.

# for user in User.objects.all():
#     Token.objects.get_or_create(user=user)

class UserView(APIView):

    def get(self, request):
        items = Products.objects.all()
        serializer = ProductSerializer(items, many=True)
        return Response(serializer.data)

class Register(APIView):

    def post(self, request):
        username = request.data['username']
        password = request.data['password']
        email_id = request.data['email_id']
        phone_number = request.data['phone_number']

        if Users.objects.filter(username=username).exists():
            return Response({'Error' : 'Username already exists'})
        elif Users.objects.filter(email_id=email_id).exists():
            user = Users.objects.get(email_id=email_id)
            if user.username:
                return Response({'Error': 'User already exists'})
            else:
                user.username = username
                user.password = password
                user.phone_number = phone_number
                user.save()
                return Response({'Success' : 'Account linked with ' + user.email_id + '. Username set as ' + user.username + '. Password set as ' + user.password})
        elif Users.objects.filter(phone_number=phone_number).exists():
            return Response({'Error': 'Phone number already exists'})

        serializer = UserSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            # print(serializer)
            if serializer.save():
                return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.data, status=status.HTTP_400_BAD_REQUEST)

class Login(APIView):

    def post(self, request):
        username = request.POST.get('username')
        password = request.POST.get('password')
        print (username, password)

        try:
            user = Users.objects.get(username = username)
            if user and user.password == password:
                print("USER MIL GAYA!")
                return Response({'Success': 'Logged In as ' + user.email_id}, status.HTTP_200_OK)
            elif user and user.password != password:
                return Response({'Error' : 'Username and Password dont match'})
        except Users.DoesNotExist:
            return Response({'Error': 'No such user exists, Register first'})
        return JsonResponse({'Error': 'Account Disabled'})

class Email(APIView):

    def post(self, request):
        email_id = request.POST.get('email_id')

        try:
            user = Users.objects.get(email_id = email_id)
            if user:
                return Response({'Check Successfull': 'Email Id exists' + user.email_id}, status.HTTP_200_OK)
        except Users.DoesNotExist:
            user = Users(email_id = email_id)
            user.save()
            return Response({'Success': 'User Created' + user.email_id}, status.HTTP_201_CREATED)

class Profile(APIView):

    def post(self, request):
        email_id = request.data['email_id']

        try:
            strF = ""
            user = Users.objects.get(email_id = email_id)
            for product in Products.objects.filter(seller = user):
                strF = strF + " name : " + product.seller_name + " email : " + product.seller_email + " time period : " + str(product.time_period)
            if strF == "":
                return Response({'Error' : 'No Ads Posted'})
            return Response({'Success' : strF})
        except Users.DoesNotExist:
            return Response({'Not Found': 'Not Found'})

class Imageget(APIView):

    def get(self, request):
        size = (100, 50)  # size of the image to create
        im = Image.new('RGB', size)  # create the image
        draw = ImageDraw.Draw(im)  # create a drawing object that is
        # used to draw on the new image
        red = (255, 0, 0)  # color of our text
        text_pos = (10, 10)  # top-left position of our text
        text = "Hello World!"  # text to draw
        # Now, we'll do the drawing:
        draw.text(text_pos, text, fill=red)

        del draw  # I'm done drawing so I don't need this anymore

        # We need an HttpResponse object with the correct mimetype
        response = HttpResponse(content_type="image/jpeg")
        # now, we tell the image to save as a PNG to the
        # provided file-like object
        im.save(response, 'JPEG')

        return response  # and we're done!

# class Home(APIView):
#
#     def get(self, request):
